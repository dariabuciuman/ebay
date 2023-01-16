package com.ebay.server.controller;

import com.ebay.server.dto.ExpiredProductDTO;
import com.ebay.server.dto.ProductDTO;
import com.ebay.server.dto.UserDetailsDTO;
import com.ebay.server.service.UserService;
import com.ebay.server.util.CurrentUserUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/private/user")
public class UserController {

    UserService userService;
    RestTemplate restTemplate = new RestTemplate();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/info")
    public UserDetailsDTO getUserInfo() {
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

    @GetMapping(value = "/expired-products")
    public List<ExpiredProductDTO> getExpiredProductsPostedByUser() {
        String uri = "http://localhost:8081/api/public/products/posted/?id=" + CurrentUserUtil.getCurrentUser().getId().toString();
        ResponseEntity<List<ExpiredProductDTO>> expiredProducts =
                restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<ExpiredProductDTO>>() {
                });
        List<ExpiredProductDTO> expiredProductDTOS = expiredProducts.getBody();
        return expiredProductDTOS;
    }

    @GetMapping(value = "/expired-bids")
    public List<ExpiredProductDTO> getExpiredBidsPostedByUser() {
        String uri = "http://localhost:8081/api/public/products/bids/?id=" + CurrentUserUtil.getCurrentUser().getId().toString();
        ResponseEntity<List<ExpiredProductDTO>> expiredProducts =
                restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<ExpiredProductDTO>>() {
                });
        List<ExpiredProductDTO> expiredProductDTOS = expiredProducts.getBody();
        return expiredProductDTOS;
    }
}
