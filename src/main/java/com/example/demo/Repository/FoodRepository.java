package com.example.demo.Repository;

import com.example.demo.model.FoodModel;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;  
import java.util.Optional;

@Repository
public interface  FoodRepository extends MongoRepository<FoodModel, String>{
    Optional<FoodModel> findById(String id);
    List<FoodModel> findByName(String name);
    Page<FoodModel> findAll(Pageable pageable);
}


