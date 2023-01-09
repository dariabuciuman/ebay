package com.ebay.server.service;

import com.ebay.server.dto.ProductDTO;
import com.ebay.server.exception.ProductException;
import com.ebay.server.model.Product;
import com.ebay.server.model.User;
import com.ebay.server.repo.ProductRepository;
import com.ebay.server.util.CurrentUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductService {

    private ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductByID(Long productId) {
        return productRepository.findProductByProductId(productId);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();
        productList.forEach((product -> {
            productDTOS.add(productDAOToDTO(product));
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

    public void checkExpiryForProduct(ProductDTO productDTO) {
        // if expiry date < now, set isActive to false
    }

    private ProductDTO productDAOToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setName(product.getName());
        productDTO.setManufacturer(product.getManufacturer());
        productDTO.setDescription(product.getDescription());
        productDTO.setCondition(product.getCondition());
        productDTO.setStartingPrice(product.getStartingPrice());
        productDTO.setHighestPrice(product.getHighestPrice());
        productDTO.setPublishDate(product.getPublishDate());
        productDTO.setExpiryDate(product.getExpiryDate());
        productDTO.setActive(product.isActive());
        return productDTO;
    }
}
