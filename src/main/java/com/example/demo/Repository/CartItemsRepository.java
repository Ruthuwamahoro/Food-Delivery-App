package com.example.demo.Repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CartItemsModel;


@Repository
public interface CartItemsRepository extends MongoRepository<CartItemsModel, String> {
    List<CartItemsModel> findByCartId(String cartId);
    Optional<CartItemsModel> findById(String id);
    Optional<CartItemsModel> findByCartIdAndFoodId(String id, String foodId);
}