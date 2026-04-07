package com.example.demo.utils;

import java.security.Key;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class Auth {
    private final static Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final static long EXPIRATION = 86400000;

    public void Info(){
        System.out.println("SECRET KEY++++++++++++++++++" + SECRET_KEY);
    }
}
