package com.example.demo.controller;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserModel;
import com.example.demo.services.AuthService;
import com.example.demo.services.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseCookie;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        String token = null;

        // 1. Try Authorization header first (for Postman/Thunder testing)
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authService.getToken(authHeader); // reuse your existing method

            // Set cookie so browser stores it going forward
            ResponseCookie cookie = ResponseCookie.from("auth-token", token)
                    .httpOnly(true)
                    .secure(false)        // set true in production
                    .sameSite("Lax")
                    .path("/")
                    .maxAge(Duration.ofDays(7))
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        }

        // 2. Fall back to cookie (for browser/Next.js requests)
        if (token == null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("auth-token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {

            String userId = authService.getUserId(token);
            System.out.println("===========================" + userId);


            Optional<UserModel> user = userService.getUserById(userId);


            if (user.isEmpty()) {
                return ResponseEntity.status(404).body("User not found");
            }

            return ResponseEntity.ok(user.get());

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
    }
    @GetMapping("/token")
    public ResponseEntity<?> getToken(HttpServletRequest request) {
        String token = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("auth-token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            return ResponseEntity.status(401).body("No token found");
        }

        return ResponseEntity.ok(Map.of("token", token));
    }
}
