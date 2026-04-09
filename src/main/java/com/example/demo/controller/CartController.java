package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CartModel;
import com.example.demo.services.AuthService;
import com.example.demo.services.CartService;
import com.example.demo.utils.SendResponse;

@RestController
@RequestMapping(value="/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private AuthService authService;

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SendResponse<CartModel>> CreateCart(@RequestBody CartModel body){
        boolean isAuthenticated = authService.isAuthenticated();
        if(isAuthenticated){
            SendResponse<CartModel> response = new SendResponse<CartModel>("error", "Unauthorized", null);
            return ResponseEntity.status(401).body(response);
        }

        try {
            CartModel createCart = cartService.createAdd(body);
            SendResponse<CartModel> response = new SendResponse<>("success", "Food item updated successfully", null);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            SendResponse<CartModel> response = new SendResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }


    }
    
}
