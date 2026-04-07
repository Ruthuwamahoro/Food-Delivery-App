package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserModel;
import com.example.demo.services.UserService;
import com.example.demo.utils.SendResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private  UserService userService;

    @GetMapping
    public  ResponseEntity<SendResponse<UserModel>> AllUsers(){
        List<UserModel> users = userService.getAllUsers();
        if(users == null){
            return ResponseEntity.notFound().build();
        } else {
            SendResponse<UserModel> response= new SendResponse("Success", "all users retrieved successfully", users);
            return ResponseEntity.status(200).body(response);
        }
    }
}
