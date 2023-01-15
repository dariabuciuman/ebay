package com.ebay.server.dto;

import lombok.Data;

@Data
public class BidDTO {
    private Long productId;
    private Float highestPrice;
}
