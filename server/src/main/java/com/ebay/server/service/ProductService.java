package com.ebay.server.service;

import com.ebay.server.dto.ProductDTO;
import com.ebay.server.model.Product;
import com.ebay.server.repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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
            productDTO.setCondition(product.getCondition());
            productDTO.setName(product.getName());
            productDTO.setDescription(product.getDescription());
            productDTO.setManufacturer(product.getManufacturer());
            productDTO.setPrice(product.getPrice());
            productDTOS.add(productDTO);
        }));
        return productDTOS;
    }
}
