package com.ebay.server.controller;

import com.ebay.server.dto.ProductDTO;
import com.ebay.server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts(){
        return productService.getAllProducts();
    }

    @PostMapping(value ="/add", consumes = "application/json")
    public void addProduct(@RequestBody ProductDTO productDTO) {
        productService.addProduct(productDTO);
    }


}
