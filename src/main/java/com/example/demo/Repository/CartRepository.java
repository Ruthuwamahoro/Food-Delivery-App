package com.example.demo.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.CartModel;

public interface CartRepository extends MongoRepository<CartModel, String>{
    
}
