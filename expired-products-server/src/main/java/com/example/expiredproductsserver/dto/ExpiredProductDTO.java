package com.example.expiredproductsserver.dto;

import lombok.Data;
import java.util.Date;

@Data
public class ExpiredProductDTO {
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

    private Long sellerId;

    private Long bidderId;
}
