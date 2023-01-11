package com.ebay.server.dto;

import lombok.Data;

@Data
public class UserDetailsDTO {
    private Long userID;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
}
