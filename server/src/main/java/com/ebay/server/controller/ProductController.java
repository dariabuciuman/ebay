package com.ebay.server.controller;

import com.ebay.server.dto.ProductDTO;
import com.ebay.server.exception.ProductException;
import com.ebay.server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/public/products/getProduct/{productId}")
    public ProductDTO getProductByID(@PathVariable("productId") Long productId) {
        return productService.getProductDTOByID(productId);
    }

    @GetMapping(value = "/public/products")
    public List<ProductDTO> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping(value = "/public/products/search")
    public  List<ProductDTO> searchProducts(@RequestParam String query) {
        return productService.searchProductsByQuery(query);
    }

    @PostMapping(value ="/private/products/add", consumes = "application/json")
    public void addProduct(@RequestBody ProductDTO productDTO) {
        productService.addProduct(productDTO);
    }

    @PostMapping(value = "/private/products/bid", consumes = "application/json")
    public void placeBid(@RequestBody ProductDTO productDTO) throws ProductException {
        productService.addHighestPrice(productDTO);
    }

    @DeleteMapping(value = "/private/products/delete")
    public void deleteProduct(@RequestParam Long productId){
        productService.deleteProductById(productId);
    }
}
