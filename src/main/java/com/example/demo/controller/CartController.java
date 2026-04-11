package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.CartDTO;
import com.example.demo.model.CartModel;
import com.example.demo.services.AuthService;
import com.example.demo.services.CartService;
import com.example.demo.utils.SendResponse;

@RestController
@RequestMapping(value="/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private AuthService authService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SendResponse<CartModel>> CreateCart(
        @RequestHeader(value = "Authorization", required = true) String authHeader
    ){
        boolean isAuthenticated = authService.isAuthenticated(authHeader);
        if(!isAuthenticated){  
            SendResponse<CartModel> response = new SendResponse<>("error", "Unauthorized", null);
            return ResponseEntity.status(401).body(response);
        }
    
        try {
            String token = authService.getToken(authHeader);
            String userId = authService.getUserId(token);   
    
            // get existing cart or create new one
            CartModel cart = cartService.getOrCreateCart(userId);
    
            SendResponse<CartModel> response = new SendResponse<>("success", "Cart retrieved successfully", cart);
            return ResponseEntity.status(HttpStatus.OK).body(response);
            
        } catch (Exception e) {
            SendResponse<CartModel> response = new SendResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SendResponse<CartDTO>> getCartDetails(
        @RequestHeader(value = "Authorization", required = true) String authHeader
    ) {
        boolean isAuthenticated = authService.isAuthenticated(authHeader);
        if (!isAuthenticated) {
            return ResponseEntity.status(401)
                .body(new SendResponse<>("error", "Unauthorized", null));
        }

        try {
            String token = authService.getToken(authHeader);
            String userId = authService.getUserId(token);

            CartDTO cart = cartService.getCartDetails(userId);

            if (cart == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new SendResponse<>("error", "Cart not found", null));
            }

            return ResponseEntity.status(HttpStatus.OK)
                .body(new SendResponse<>("success", "Cart fetched successfully", cart));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new SendResponse<>("error", e.getMessage(), null));
        }
    }

    
}