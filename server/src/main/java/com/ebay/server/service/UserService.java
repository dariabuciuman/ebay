package com.ebay.server.service;

import com.ebay.server.dto.ProductDTO;
import com.ebay.server.dto.UserDetailsDTO;
import com.ebay.server.model.Product;
import com.ebay.server.model.User;
import com.ebay.server.repo.ProductRepository;
import com.ebay.server.util.CurrentUserUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ebay.server.util.ProductUtil.productDAOToDTO;

@Service
public class UserService {

    private ProductRepository productRepository;

    public UserService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public UserDetailsDTO getUserDetails(){
        User user = CurrentUserUtil.getCurrentUser();
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setUserID(user.getId());
        userDetailsDTO.setUsername(user.getUsername());
        userDetailsDTO.setFirstName(user.getFirstName());
        userDetailsDTO.setLastName(user.getLastName());
        userDetailsDTO.setEmail(user.getEmail());
        return userDetailsDTO;
    }

    public List<ProductDTO> getProductsPostedByUser(){
        User user = CurrentUserUtil.getCurrentUser();
        List<Product> productList = productRepository.findAllBySellerId(user.getId());
        List<ProductDTO> productDTOS = new ArrayList<>();
        productList.forEach((product -> {
            productDTOS.add(productDAOToDTO(product));
        }));
        return productDTOS;
    }

    public List<ProductDTO> getBidsPostedByUser(){
        User user = CurrentUserUtil.getCurrentUser();
        List<Product> productList = productRepository.findAllByBidderId(user.getId());
        List<ProductDTO> productDTOS = new ArrayList<>();
        productList.forEach((product -> {
            productDTOS.add(productDAOToDTO(product));
        }));
        return productDTOS;
    }
}
