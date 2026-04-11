package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CartItemsModel;
import com.example.demo.services.AuthService;
import com.example.demo.services.CartItemsService;
import com.example.demo.utils.SendResponse;

@RestController
@RequestMapping(value="/api/carts/items")
public class CartItemController {

    @Autowired
    private AuthService authService;

    @Autowired
    private CartItemsService cartItemsService;


    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SendResponse<CartItemsModel>> addItemsToCart(@RequestBody CartItemsModel body, @RequestHeader(value = "Authorization", required = true) String authHeader){
        boolean isAuthenticated = authService.isAuthenticated(authHeader);
        if(!isAuthenticated){  
            SendResponse<CartItemsModel> response = new SendResponse<>("error", "Unauthorized", null);
            return ResponseEntity.status(401).body(response);
        }

        try {
            if(body.getFoodId() == null || body.getQuantity() == null || body.getPrice() == null){
                System.out.println("+++++" +body);
                SendResponse<CartItemsModel> response = new SendResponse<>("error", "All fields are required", null);
                return ResponseEntity.status(401).body(response);
            }

            CartItemsModel cartData = new CartItemsModel();
            cartData.generateId();
            cartData.setFoodId(body.getFoodId());
            cartData.setQuantity(body.getQuantity());
            cartData.setPrice(body.getPrice());
            cartData.setCreatedAt();
            cartData.setUpdatedAt();

            cartItemsService.AddItemsToCart(cartData);
            SendResponse<CartItemsModel> response = new SendResponse<>("success", "Cart items added successfully", null);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            SendResponse<CartItemsModel> response = new SendResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}
