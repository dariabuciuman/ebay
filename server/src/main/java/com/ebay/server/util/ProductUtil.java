package com.ebay.server.util;

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
        return productDTO;
    }
}
