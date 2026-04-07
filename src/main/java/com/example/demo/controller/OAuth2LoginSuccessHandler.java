package com.example.demo.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.Repository.RoleRepository;
import com.example.demo.model.UserModel;
import com.example.demo.model.RoleModel;
import com.example.demo.services.AuthService;
import com.example.demo.services.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler{
    // private static final Logger logger = LoggerFactory.getLogger(OAuth2LoginSuccessHandler.class);
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException,ServletException{
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        String fullName = oauthUser.getAttribute("name");
        String picture = oauthUser.getAttribute("picture");
        Optional<RoleModel> defaultRole = roleRepository.findByName("User");

        UserModel user = new UserModel();
        user.generateId();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPicture(picture);
        user.setCreatedAt();
        user.setUpdatedAt();
        defaultRole.ifPresent(role-> user.setRoleId(role.getId()));
        UserModel savedUser = userService.registerUser(user);

        String token = authService.generateJWT(user.getId(), user.getEmail(), user.getRoleId());        
    }

    
}
