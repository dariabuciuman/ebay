package com.ebay.server.util;

import com.ebay.server.dto.ExpiredProductDTO;
import com.ebay.server.dto.ProductDTO;
import com.ebay.server.model.Product;

public class ProductUtil {

    public static ProductDTO productDAOToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setName(product.getName());
        productDTO.setManufacturer(product.getManufacturer());
        productDTO.setDescription(product.getDescription());
        productDTO.setCondition(product.getCondition());
        productDTO.setStartingPrice(product.getStartingPrice());
        productDTO.setHighestPrice(product.getHighestPrice());
        productDTO.setPublishDate(product.getPublishDate());
        productDTO.setExpiryDate(product.getExpiryDate());
        productDTO.setActive(product.isActive());
        productDTO.setSellerId(product.getSeller().getId());
        if(product.getBidder() != null)
            productDTO.setBidderId(product.getBidder().getId());
        return productDTO;
    }

    public static ExpiredProductDTO expiredProductDAOToDTO(Product product) {
        ExpiredProductDTO expiredProductDTO = new ExpiredProductDTO();
        expiredProductDTO.setProductId(product.getProductId());
        expiredProductDTO.setName(product.getName());
        expiredProductDTO.setManufacturer(product.getManufacturer());
        expiredProductDTO.setDescription(product.getDescription());
        expiredProductDTO.setCondition(product.getCondition());
        expiredProductDTO.setStartingPrice(product.getStartingPrice());
        //if(product.getHighestPrice() != null)
            expiredProductDTO.setHighestPrice(product.getHighestPrice());
        //else expiredProductDTO.setHighestPrice((float) 0);
        expiredProductDTO.setPublishDate(product.getPublishDate());
        expiredProductDTO.setExpiryDate(product.getExpiryDate());
        expiredProductDTO.setActive(product.isActive());
        expiredProductDTO.setSellerId(product.getSeller().getId());
        if(product.getBidder() != null)
            expiredProductDTO.setBidderId(product.getBidder().getId());
        else expiredProductDTO.setBidderId(null);
        return expiredProductDTO;
    }
}
