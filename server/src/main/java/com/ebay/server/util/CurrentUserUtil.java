package com.ebay.server.util;

import com.ebay.server.model.ShopUserDetails;
import com.ebay.server.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserUtil{
    public static User getCurrentUser() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        ShopUserDetails shopUserDetails = (ShopUserDetails) authentication.getPrincipal();
        User user = shopUserDetails.getUser();
        return user;
    }
}
