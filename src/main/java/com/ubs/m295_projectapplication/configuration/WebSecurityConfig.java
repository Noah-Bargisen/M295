package com.ubs.m295_projectapplication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/m295/**").permitAll()
                        .requestMatchers("/m295/software/**").permitAll()
                        .anyRequest().authenticated()
                );


        return http.build();
    }
    /*
    @Bean
    UserDetailsService userDetailsService() {
        return new UserDetailServiceImpl();
    }*/
}
