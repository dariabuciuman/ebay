package com.ebay.server.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private String name;

    private String manufacturer;

    private String description;

    private String condition;

    private Float price;
}
