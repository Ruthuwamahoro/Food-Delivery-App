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
import com.example.demo.model.CartModel;
import com.example.demo.services.AuthService;
import com.example.demo.services.CartItemsService;
import com.example.demo.services.CartService;
import com.example.demo.utils.SendResponse;

@RestController
@RequestMapping(value="/api/carts/items")
public class CartItemController {

    @Autowired
    private AuthService authService;

    @Autowired
    private CartItemsService cartItemsService;

    @Autowired
    private CartService cartService;


    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SendResponse<CartItemsModel>> addItemsToCart(
        @RequestBody CartItemsModel body, 
        @RequestHeader(value = "Authorization", required = true) String authHeader
    ){
        boolean isAuthenticated = authService.isAuthenticated(authHeader);
        if(!isAuthenticated){  
            return ResponseEntity.status(401)
                .body(new SendResponse<>("error", "Unauthorized", null));
        }
    
        try {
            if(body.getFoodId() == null || body.getQuantity() == null || body.getPrice() == null){
                return ResponseEntity.status(400)
                    .body(new SendResponse<>("error", "All fields are required", null));
            }
    
            String token = authService.getToken(authHeader);
            String userId = authService.getUserId(token);
    
            CartModel cart = cartService.getOrCreateCart(userId); 
           
            
            CartItemsModel cartData = new CartItemsModel();
            cartData.generateId();
            cartData.setCartId(cart.getId()); 
            cartData.setFoodId(body.getFoodId());
            cartData.setQuantity(body.getQuantity());
            cartData.setPrice(body.getPrice());
            cartData.setCreatedAt();
            cartData.setUpdatedAt();
    
            cartItemsService.AddItemsToCart(cartData);
    
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SendResponse<>("success", "Item added to cart successfully", cartData));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new SendResponse<>("error", e.getMessage(), null));
        }
    }


}
