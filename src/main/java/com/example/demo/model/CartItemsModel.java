package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Document(collection = "CartItems")
@Data
@ToString
public class CartItemsModel {

    @Id
    private String id;
    private String foodId;
    private String quantity;
    private Integer price;
    private Integer totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void generateId(){
        if(this.id == null){
            this.id = UUID.randomUUID().toString();
        }
    }

    public LocalDateTime setCreatedAt(){
        return this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime setUpdatedAt(){
        return this.updatedAt = LocalDateTime.now();
    }
    
}
