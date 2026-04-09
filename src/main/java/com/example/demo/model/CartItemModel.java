package com.example.demo.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Document(collection="cart-items")
@Data
public class CartItemModel {

    @Id
    private String id;
    private String cartId;
    private String foodId;
    private Integer quantity;
    private Integer priceAtTime;

    public void generateId(){
        if(this.id == null){
            this.id = UUID.randomUUID().toString();
        }
    }
    
}
