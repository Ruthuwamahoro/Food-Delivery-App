package com.example.demo.controller;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.model.UserModel;
import com.example.demo.services.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler{
    private static final Logger logger = LoggerFactory.getLogger(OAuth2LoginSuccessHandler.class);



    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException,ServletException{
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("name");
        String fullName = oauthUser.getAttribute("name");
        UserModel user = new UserModel();
        user.setFullName(fullName);
        user.setEmail(email);
        userService.registerUser(user);

        logger.info("Google OAuth2 attributes: {}", oauthUser.getAttributes());
        logger.info("Full Name: {}", fullName);
        logger.info("Email: {}", email);

        response.sendRedirect("/api/users/{id}");
        System.out.println("this is the registered user====================" + user);
    }

    
}
