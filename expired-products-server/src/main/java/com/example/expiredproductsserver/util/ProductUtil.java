package com.example.expiredproductsserver.util;

import com.example.expiredproductsserver.dto.ExpiredProductDTO;
import com.example.expiredproductsserver.model.Product;

public class ProductUtil {
    public static ExpiredProductDTO expiredProductDAOToDTO(Product product) {
        ExpiredProductDTO expiredProductDTO = new ExpiredProductDTO();
        expiredProductDTO.setProductId(product.getProductId());
        expiredProductDTO.setName(product.getName());
        expiredProductDTO.setManufacturer(product.getManufacturer());
        expiredProductDTO.setDescription(product.getDescription());
        expiredProductDTO.setCondition(product.getCondition());
        expiredProductDTO.setStartingPrice(product.getStartingPrice());
        expiredProductDTO.setHighestPrice(product.getHighestPrice());
        expiredProductDTO.setPublishDate(product.getPublishDate());
        expiredProductDTO.setExpiryDate(product.getExpiryDate());
        expiredProductDTO.setActive(product.isActive());
        expiredProductDTO.setSellerId(product.getSellerId());
        expiredProductDTO.setBidderId(product.getBidderId());
        return expiredProductDTO;
    }

    public static Product productDTOToDAO(ExpiredProductDTO expiredProductDTO) {
        Product product = new Product();
        product.setProductId(expiredProductDTO.getProductId());
        product.setName(expiredProductDTO.getName());
        product.setManufacturer(expiredProductDTO.getManufacturer());
        product.setDescription(expiredProductDTO.getDescription());
        product.setCondition(expiredProductDTO.getCondition());
        product.setStartingPrice(expiredProductDTO.getStartingPrice());
        product.setHighestPrice(expiredProductDTO.getHighestPrice());
        product.setPublishDate(expiredProductDTO.getPublishDate());
        product.setExpiryDate(expiredProductDTO.getExpiryDate());
        product.setActive(expiredProductDTO.isActive());
        product.setSellerId(expiredProductDTO.getSellerId());
        product.setBidderId(expiredProductDTO.getBidderId());
        return product;
    }
}
