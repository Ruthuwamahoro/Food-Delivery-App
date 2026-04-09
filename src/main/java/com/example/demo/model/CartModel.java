package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Document(collection="cart")
@Data
public class CartModel {

    @Id
    private String id;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void generateId(){
        if(this.id == null){
            this.id = UUID.randomUUID().toString();
        }
    }
    
}
