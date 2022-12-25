package com.ebay.server.security;

import com.ebay.server.filter.AuthTokenFilter;
import com.ebay.server.service.AuthEntryPointJwt;
import com.ebay.server.service.ShopUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration{
    private final ShopUserDetailsService userDetailsService;
    private final UserPasswordEncoder userPasswordEncoder;
    private final AuthEntryPointJwt entryPointJwt;
    private final AuthTokenFilter authTokenFilter;

    public WebSecurityConfiguration(ShopUserDetailsService userDetailsService, UserPasswordEncoder userPasswordEncoder, AuthEntryPointJwt entryPointJwt, AuthTokenFilter authTokenFilter) {
        this.userDetailsService = userDetailsService;
        this.userPasswordEncoder = userPasswordEncoder;
        this.entryPointJwt = entryPointJwt;
        this.authTokenFilter = authTokenFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    @Primary
    public AuthenticationManagerBuilder configureAuthenticationManagerBuilder(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(userPasswordEncoder.getEncoder());
        return authenticationManagerBuilder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(entryPointJwt).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/public/products/add").authenticated()
                .antMatchers("/api/public/products/**").permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
