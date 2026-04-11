package com.example.demo.Repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CartItemsModel;


@Repository
public interface CartItemsRepository extends MongoRepository<CartItemsModel, String> {
    List<CartItemsModel> findByCartId(String cartId);
}