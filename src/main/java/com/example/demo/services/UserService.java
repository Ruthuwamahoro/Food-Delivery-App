package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.UserRepository;
import com.example.demo.model.UserModel;

@Service
public class UserService {
    private  UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserModel registerUser(UserModel user){
        Optional<UserModel> existing = userRepository.findByEmail(user.getEmail());
        if(existing.isPresent()){
            return null;
        }
        user.generateId();
        UserModel saveUser =  userRepository.save(user);
        return saveUser;
    }

    public static Optional<UserModel> getUserById(String id){
        Optional<UserModel> user = userRepository.findById(id);
        return user;
    }
    
}
