package com.ubs.m295_projectapplication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration

public class WebSecurityConfiguration {
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
        return manager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/project/admin/**")
                        .hasRole("USER")
                        .requestMatchers("/team/admin/**")
                        .hasRole("USER")
                        .requestMatchers("/software/admin/**")
                        .hasRole("USER")
                        .requestMatchers("/teamMember/admin/**")
                        .hasRole("USER")
                        .anyRequest()
                        .permitAll())
                .httpBasic(Customizer.withDefaults())
                .csrf((csrf) -> csrf.disable());


        //
        //http.authorizeHttpRequests((request) -> request
        //                .anyRequest()
        //                .permitAll())
        //        .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
