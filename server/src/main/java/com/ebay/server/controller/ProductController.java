package com.ebay.server.controller;

import com.ebay.server.dto.ProductDTO;
import com.ebay.server.exception.ProductException;
import com.ebay.server.model.Product;
import com.ebay.server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/private/products/getProduct/{productId}")
    public Product getProductByID(@PathVariable("productId") Long productId) {
        return productService.getProductByID(productId);
    }

    @GetMapping(value = "/public/products")
    public List<ProductDTO> getAllProducts(){
        return productService.getAllProducts();
    }

    @PostMapping(value ="/private/products/add", consumes = "application/json")
    public void addProduct(@RequestBody ProductDTO productDTO) {
        productService.addProduct(productDTO);
    }

    @PostMapping(value = "/private/products/bid", consumes = "application/json")
    public void placeBid(@RequestBody ProductDTO productDTO) throws ProductException {
        productService.addHighestPrice(productDTO);
    }
}
