package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.RoleModel;

@Repository
public interface RoleRepository extends MongoRepository<RoleModel, String>{
    Optional<RoleModel> findByName(String Name); 
}