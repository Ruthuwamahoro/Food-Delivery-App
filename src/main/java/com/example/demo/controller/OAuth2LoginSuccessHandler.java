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
import com.example.demo.services.CloudinaryService;
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

    @Autowired
    private CloudinaryService cloudinaryService;    

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        String fullName = oauthUser.getAttribute("name");
        String picture = oauthUser.getAttribute("picture");
    
        UserModel user = userService.findByEmail(email);
    
        if (user == null) {
            String cloudinaryPictureUrl = null;
            if (picture != null) {
                cloudinaryPictureUrl = cloudinaryService.uploadFromUrl(picture, "users/profiles");
            }
            Optional<RoleModel> defaultRole = roleRepository.findByName("User");
            UserModel newUser = new UserModel();
            newUser.generateId();
            newUser.setFullName(fullName);
            newUser.setEmail(email);
            newUser.setPicture(cloudinaryPictureUrl); 
            newUser.setCreatedAt();
            newUser.setUpdatedAt();
            defaultRole.ifPresent(role -> newUser.setRoleId(role.getId()));
            user = userService.registerUser(newUser); 
        }
    
        String token = authService.generateJWT(user.getId(), user.getEmail(), user.getRoleId());
    
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("{\"token\": \"" + token + "\"}");
        response.getWriter().flush();
    }

}
