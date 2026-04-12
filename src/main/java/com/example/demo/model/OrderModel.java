package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo.interfaces.OrderStatus;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Document(collection="orders")
@Data
@ToString
public class OrderModel {
    @Id
    private String id;
    private String cartId;
    private String userId;
    private List<ItemsOrder> items;
    private Integer totalAmount;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public void generateId(){
        if(this.id == null){
            this.id = UUID.randomUUID().toString();
        }
    
    };

    public LocalDateTime setCreatedAt(){
        return this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime setUpdatedAt(){
        return this.updatedAt = LocalDateTime.now();
    }

    @Data
    public static class ItemsOrder{
        private String id;
        private String name;
        private String images;
        private Integer price;
        private Integer quantity;
        private Integer totalPrice;
    }
}
