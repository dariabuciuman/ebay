package com.ebay.server.service;

import com.ebay.server.dto.ExpiredProductDTO;
import com.ebay.server.dto.ProductDTO;
import com.ebay.server.exception.ProductException;
import com.ebay.server.model.Product;
import com.ebay.server.model.User;
import com.ebay.server.repo.ProductRepository;
import com.ebay.server.util.CurrentUserUtil;
import com.ebay.server.util.ProductUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ProductService {

    private ProductRepository productRepository;
    private final KafkaTemplate<String, ExpiredProductDTO> kafkaTemplate;

    public ProductService(ProductRepository productRepository, KafkaTemplate<String, ExpiredProductDTO> kafkaTemplate) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public ProductDTO getProductDTOByID(Long productId) {
        Product product = productRepository.findProductByProductId(productId);
        return ProductUtil.productDAOToDTO(product);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();
        productList.forEach((product -> {
            productDTOS.add(ProductUtil.productDAOToDTO(product));
            checkExpiryForProduct(product);
        }));
        return productDTOS;
    }

    public void addProduct(ProductDTO productDTO) {
        User user = CurrentUserUtil.getCurrentUser();
        log.info("Trying to add product {}...", productDTO.getName());
        log.info("Auth: {}" + user);
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setManufacturer(productDTO.getManufacturer());
        product.setDescription(productDTO.getDescription());
        product.setCondition(productDTO.getCondition());
        product.setStartingPrice(productDTO.getStartingPrice());
        product.setHighestPrice(productDTO.getHighestPrice());
        product.setPublishDate(productDTO.getPublishDate());
        product.setExpiryDate(productDTO.getExpiryDate());
        product.setActive(true);
        product.setSeller(user);
        product.setBidder(null);
        productRepository.save(product);
        log.info("Product {} was added successfully", productDTO.getName());
    }

    public void addHighestPrice(ProductDTO productDTO) throws ProductException {
        User bidder = CurrentUserUtil.getCurrentUser();
        Product product = productRepository.findProductByProductId(productDTO.getProductId());
        if (!bidder.getId().equals(product.getSeller().getId())) {
            if (productDTO.getHighestPrice() > product.getStartingPrice() && product.getHighestPrice() != null && productDTO.getHighestPrice() > product.getHighestPrice()) {
                product.setHighestPrice(productDTO.getHighestPrice());
                product.setBidder(bidder);
                productRepository.save(product);
            }
        } else throw new ProductException(ProductException.INCORRECT_BID);
    }

    public void checkExpiryForProduct(Product product) {
        final KafkaProducerService producerService = new KafkaProducerService(kafkaTemplate);
        // if expiry date < now, set isActive to false
        if(new Date().after(product.getExpiryDate())) {
            product.setActive(false);
            ExpiredProductDTO expiredProductDTO = ProductUtil.expiredProductDAOToDTO(product);
            producerService.sendMessage("expiredProducts", expiredProductDTO);
            productRepository.delete(product);
        }
    }
}
