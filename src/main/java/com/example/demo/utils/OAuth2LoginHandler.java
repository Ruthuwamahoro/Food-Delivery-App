package com.example.demo.utils;

import java.security.Key;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class OAuth2LoginHandler extends SimpleUrlAuthenticationSuccessHandler{
    private final static Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final static long EXPIRATION = 86400000;

    public void Info(){
        System.out.println("SECRET KEY++++++++++++++++++" + SECRET_KEY);
    }
}
