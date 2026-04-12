package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.OrderModel;

@Repository
public interface OrderRepository extends MongoRepository<OrderModel, String> {
    List<OrderModel> findByUserId(String userId);
}
