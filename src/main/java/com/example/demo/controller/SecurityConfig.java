package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users/**")
                .permitAll()
                .requestMatchers("/api/foods/**").permitAll()
                .requestMatchers("/api/roles/**").permitAll()
                .requestMatchers("/api/carts/**").permitAll()
                .anyRequest().authenticated())
            .oauth2Login(oauth2 -> oauth2.successHandler(oAuth2LoginSuccessHandler))
            .csrf(csrf -> csrf.disable());
            // .cors(cors -> cors.disable());
        return http.build();
    }
    
}
