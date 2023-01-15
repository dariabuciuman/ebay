package com.example.expiredproductsserver.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProductDTO {
    private Long productId;

    private String name;

    private String manufacturer;

    private String description;

    private String condition;

    private Float startingPrice;

    private Float highestPrice;

    private Date publishDate;

    private Date expiryDate;

    private boolean isActive;
}