package com.example.expiredproductsserver.controller;

import com.example.expiredproductsserver.dto.ExpiredProductDTO;
import com.example.expiredproductsserver.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/products")
@CrossOrigin(origins = "http://localhost:8080")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/all")
    public List<ExpiredProductDTO> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping(value = "/posted")
    public List<ExpiredProductDTO> getExpiredProductsPostedByUser(@RequestParam Long id){
        return productService.getExpiredProductsPostedByUser(id);
    }

    @GetMapping(value = "/bids")
    public List<ExpiredProductDTO> getExpiredBidsPostedByUser(@RequestParam Long id){
        return productService.getExpiredBidsPostedByUser(id);
    }
}
