package com.ebay.server.service;

import com.ebay.server.dto.JwtResponseDTO;
import com.ebay.server.dto.RegisterUserDTO;
import com.ebay.server.dto.SignInUserDTO;
import com.ebay.server.enums.Roles;
import com.ebay.server.exception.UserException;
import com.ebay.server.model.Role;
import com.ebay.server.model.ShopUserDetails;
import com.ebay.server.model.User;
import com.ebay.server.repo.RoleRepository;
import com.ebay.server.repo.UserRepository;
import com.ebay.server.security.UserPasswordEncoder;
import com.ebay.server.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthHelperService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserPasswordEncoder userPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public void registerUser(RegisterUserDTO registerUser) throws UserException {
        log.info("Trying to register user {}", registerUser.getUsername());
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
            Role userRole = roleRepository.findByName(Roles.USER);
            user.setRoles(Collections.singleton(userRole));
            userRepository.save(user);
            log.info("User {} registered successfully", user.getUsername());
        }
    }

    public JwtResponseDTO signInUser(SignInUserDTO signInUser) {
        log.info("Trying to sign in user {}", signInUser.getUsername());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInUser.getUsername(),signInUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.generateJwtToken(authentication);

        ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        JwtResponseDTO response = new JwtResponseDTO();
        response.setToken(jwt);
        response.setEmail(userDetails.getEmail());
        response.setUsername(userDetails.getUsername());
        response.setRoles(roles);
        log.info("User {} signed in", userDetails.getUsername());

        return response;
    }

}
