package com.ebay.server.controller;

import com.ebay.server.dto.JwtResponseDTO;
import com.ebay.server.dto.RegisterUserDTO;
import com.ebay.server.dto.SignInUserDTO;
import com.ebay.server.exception.UserException;
import com.ebay.server.service.AuthHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthHelperService authHelperService;

    @PostMapping(value = "/register", consumes = "application/json")
    public void registerUser(@RequestBody RegisterUserDTO registerUser) throws UserException {
        authHelperService.registerUser(registerUser);
    }

    @PostMapping(value = "/signin", consumes = "application/json")
    public JwtResponseDTO signInUser(@RequestBody SignInUserDTO signInUser) {
        return authHelperService.signInUser(signInUser);
    }

}
