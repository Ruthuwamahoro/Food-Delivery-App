package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserModel;
import com.example.demo.services.AuthService;
import com.example.demo.services.UserService;
import com.example.demo.utils.SendResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private  UserService userService;

    @Autowired
    private AuthService authService;

    @GetMapping
    public  ResponseEntity<SendResponse<UserModel>> AllUsers(
        @RequestHeader(value = "Authorization", required = true) String authHeader
    ){

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            SendResponse<UserModel> response = new SendResponse<>("error", "Unauthorized", null);
            return ResponseEntity.status(401).body(response);
        }

        String token = authHeader.substring(7);

        boolean isAdmin = authService.isAdmin(token);

        if(!isAdmin){
            SendResponse<UserModel> response = new SendResponse<>("error", "Unauthorized", null);
            return ResponseEntity.status(401).body(response);
        }


        List<UserModel> users = userService.getAllUsers();
        System.out.println("****************************");

        if(users == null){
            return ResponseEntity.notFound().build();
        } else {
            SendResponse<UserModel> response= new SendResponse("Success", "all users retrieved successfully", users);
            return ResponseEntity.status(200).body(response);
        }
    }

    @GetMapping("/profile")
    public  ResponseEntity<SendResponse<UserModel>> getUserInfo(@RequestHeader(value = "Authorization", required = true) String authHeader){
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            SendResponse<UserModel> response = new SendResponse<>("error", "Unauthorized", null);
            return ResponseEntity.status(401).body(response);
        }
        try {
            
            String token = authHeader.substring(7);
            String userId = authService.getUserId(token);
            Optional<UserModel> userData = userService.getUserById(userId);
            SendResponse<UserModel> response= new SendResponse("Success", "all retrieved successfully", userData);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new SendResponse<>("error", e.getMessage(), null));
        }

    }
}
