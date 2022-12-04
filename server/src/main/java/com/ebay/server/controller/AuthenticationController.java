package com.ebay.server.controller;

import com.ebay.server.dto.RegisterUserDTO;
import com.ebay.server.exception.UserException;
import com.ebay.server.service.ShopUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthenticationController {

    @Autowired
    private ShopUserDetailsService userDetailsService;

    @PostMapping(value = "/register", consumes = "application/json")
    public void registerUser(@RequestBody RegisterUserDTO registerUser) throws UserException {
        userDetailsService.registerUser(registerUser);
    }
}
