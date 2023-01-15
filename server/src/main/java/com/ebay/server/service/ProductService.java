package com.ebay.server.service;

import com.ebay.server.dto.ProductDTO;
import com.ebay.server.exception.ProductException;
import com.ebay.server.model.Product;
import com.ebay.server.model.User;
import com.ebay.server.repo.ProductRepository;
import com.ebay.server.util.CurrentUserUtil;
import com.ebay.server.util.ProductUtil;
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

    public ProductDTO getProductDTOByID(Long productId) {
        Product product = productRepository.findProductByProductId(productId);
        return ProductUtil.productDAOToDTO(product);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();
        productList.forEach((product -> {
            productDTOS.add(ProductUtil.productDAOToDTO(product));
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
        /*TODO
        *  make method to add product to bidProducts
        *  also make method to add a product to a products list
        *  maybe make another user_products table and user_bids table
        * */
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
}
