package com.ebay.server.service;

import com.ebay.server.dto.RegisterUserDTO;
import com.ebay.server.enums.Roles;
import com.ebay.server.exception.UserException;
import com.ebay.server.model.Role;
import com.ebay.server.model.ShopUserDetails;
import com.ebay.server.model.User;
import com.ebay.server.repo.UserRepository;
import com.ebay.server.security.UserPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ShopUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPasswordEncoder userPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username" + username)
        );

        return new ShopUserDetails(user);
    }

    public void registerUser(RegisterUserDTO registerUser) throws UserException {
        if (userRepository.existsByEmail(registerUser.getEmail()))
            throw new UserException(UserException.EMAIL_TAKEN);
        else if (userRepository.existsByUsername(registerUser.getUsername()))
            throw new UserException(UserException.USERNAME_TAKEN);
        else {
            User user = new User();
            user.setEmail(registerUser.getEmail());
            user.setUsername(registerUser.getUsername());
            user.setFirstName(registerUser.getFirstName());
            user.setLastName(registerUser.getLastName());
            user.setPassword(userPasswordEncoder.encode(registerUser.getPassword()));
            user.setActive(true);
            user.setRoles(Collections.singleton(new Role(Roles.USER)));
            userRepository.save(user);
        }
    }
}
