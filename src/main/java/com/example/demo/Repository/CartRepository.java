package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CartModel;


@Repository
public interface CartRepository extends MongoRepository<CartModel, String>{
    Optional<CartModel> findByUserId(String userId);
}
