package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.OrderModel;
import com.example.demo.services.AuthService;
import com.example.demo.services.OrderService;
import com.example.demo.utils.SendResponse;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderController {

    @Autowired
    private AuthService authService;

    @Autowired
    private OrderService orderService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SendResponse<OrderModel>> createOrder(
        @RequestHeader(value = "Authorization", required = true) String authHeader
    ){
        boolean isAuthenticated = authService.isAuthenticated(authHeader);
        if (!isAuthenticated) {
            return ResponseEntity.status(401)
                .body(new SendResponse<>("error", "Unauthorized", null));
        }

        try {
            String token = authService.getToken(authHeader);
            String userId = authService.getUserId(token);

            OrderModel order = orderService.createOrder(userId);

            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SendResponse<>("success", "Order created successfully", order));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new SendResponse<>("error", e.getMessage(), null));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new SendResponse<>("error", e.getMessage(), null));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<SendResponse<List<OrderModel>>> getMyOrders(@RequestBody OrderModel body, @RequestHeader(value="Authorization", required = true) String authHeader){
        boolean isAuthenticated = authService.isAuthenticated(authHeader);
        if (!isAuthenticated) {
            return ResponseEntity.status(401)
                .body(new SendResponse<>("error", "Unauthorized", null));
        }

        try {
            String token = authService.getToken(authHeader);
            String userId = authService.getUserId(token);

            List<OrderModel> orders = orderService.getMyOrder(userId);

            return ResponseEntity.status(HttpStatus.OK)
                .body(new SendResponse<>("success", "Your Order fetched successfully", orders));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new SendResponse<>("error", e.getMessage(), null));
        }
    }
}