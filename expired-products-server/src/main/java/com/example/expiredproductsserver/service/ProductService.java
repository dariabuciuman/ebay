package com.example.expiredproductsserver.service;

import com.example.expiredproductsserver.dto.ExpiredProductDTO;
import com.example.expiredproductsserver.model.Product;
import com.example.expiredproductsserver.repo.ProductRepository;
import com.example.expiredproductsserver.util.ProductUtil;
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

    public ExpiredProductDTO getProductDTOByID(Long productId) {
        Product product = productRepository.findProductByProductId(productId);
        return ProductUtil.expiredProductDAOToDTO(product);
    }

    public List<ExpiredProductDTO> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<ExpiredProductDTO> productDTOS = new ArrayList<>();
        productList.forEach((product -> {
            productDTOS.add(ProductUtil.expiredProductDAOToDTO(product));
        }));
        return productDTOS;
    }

    public void addProduct(ExpiredProductDTO expiredProductDTO) {
        log.info("Trying to add product {}...", expiredProductDTO.getName());
        if(!productRepository.existsById(expiredProductDTO.getProductId()))
            productRepository.save(ProductUtil.productDTOToDAO(expiredProductDTO));
        log.info("Product {} was added successfully", expiredProductDTO.getName());
    }
}
