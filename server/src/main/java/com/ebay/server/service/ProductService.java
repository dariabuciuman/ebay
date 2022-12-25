package com.ebay.server.service;

import com.ebay.server.dto.ProductDTO;
import com.ebay.server.model.Product;
import com.ebay.server.model.ShopUserDetails;
import com.ebay.server.model.User;
import com.ebay.server.repo.ProductRepository;
import com.ebay.server.util.CurrentUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public List<ProductDTO> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();
        productList.forEach((product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setName(product.getName());
            productDTO.setManufacturer(product.getManufacturer());
            productDTO.setDescription(product.getDescription());
            productDTO.setCondition(product.getCondition());
            productDTO.setStartingPrice(product.getStartingPrice());
            productDTO.setHighestPrice(product.getHighestPrice());
            productDTO.setPublishDate(product.getPublishDate());
            productDTO.setExpiryDate(product.getExpiryDate());
            productDTO.setActive(product.isActive());
            productDTOS.add(productDTO);
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
        productRepository.save(product);
        log.info("Product {} was added successfully", productDTO.getName());
    }

    public void checkExpiryForProduct(ProductDTO productDTO){
        // if expiry date < now, set isActive to false
    }
}
