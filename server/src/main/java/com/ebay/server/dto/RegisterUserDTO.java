package com.ebay.server.dto;

import lombok.Data;

@Data
public class RegisterUserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
