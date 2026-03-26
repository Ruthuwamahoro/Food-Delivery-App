package com.example.demo.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserModel;
import com.example.demo.services.UserService;
import com.example.demo.utils.SendResponse;

@RestController
@RequestMapping("/api/users")
public class HelloController {

    @GetMapping
    public String hello(){
        return "welcome to food delivery app";
    }

    @GetMapping("/{id}")
    public ResponseEntity<SendResponse<UserModel>> userData(@PathVariable String id){
        Optional<UserModel> AllUserInfo = UserService.getUserById(id);
        if(AllUserInfo == null ||AllUserInfo.isEmpty()){
            SendResponse<UserModel> responseData = new SendResponse<>("success", "Not Found", null);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
        SendResponse<UserModel> response = new SendResponse<>("success", "User returned successfully", AllUserInfo.get());
        return ResponseEntity.ok(response);
    }

    
}
