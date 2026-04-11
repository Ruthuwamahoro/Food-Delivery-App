package com.example.demo.model;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.ToString;


@NoArgsConstructor
@Document(collection = "foods")
@Data
@ToString

public class FoodModel {
    
    @Id
    private String id;

    private String name;

    private String description;

    private Integer price;

    private String deliveryTime;

    private List<String> images;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void generateId(){
        if(this.id == null){
            this.id = UUID.randomUUID().toString();
        }
    
    };

    public LocalDateTime initCreatedAt(){
        return this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime initUpdatedAt(){
        return this.updatedAt = LocalDateTime.now();
    }
}
