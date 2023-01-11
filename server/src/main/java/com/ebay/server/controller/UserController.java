package com.ebay.server.controller;

import com.ebay.server.dto.ProductDTO;
import com.ebay.server.dto.UserDetailsDTO;
import com.ebay.server.service.ProductService;
import com.ebay.server.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/private/user")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/info")
    public UserDetailsDTO getUserInfo(){
        return userService.getUserDetails();
    }

    @GetMapping(value = "/products")
    public List<ProductDTO> getProductsPostedByUser() {
        return userService.getProductsPostedByUser();
    }

    @GetMapping(value = "/bids")
    public List<ProductDTO> getBidsPostedByUser() {
        return userService.getBidsPostedByUser();
    }
}
