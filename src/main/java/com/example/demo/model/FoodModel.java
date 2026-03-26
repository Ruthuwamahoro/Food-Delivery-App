package com.example.demo.model;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Document(collection = "foods")
@Setter
@Getter
@ToString

public class FoodModel {
    
    @Id
    private String id;

    private String name;

    private String description;

    private String price;

    public void generateId(){
        if(this.id == null){
            this.id = UUID.randomUUID().toString();
        }
    
    }

    public String getDescription(){
        return description;
    }

    public String getName(){
        return name;
    }
    public String getPrice(){
        return price;
    }
    public String setName(String name){
        return this.name = name;
    }
    public String setDescription(String description){
        return this.description = description;
    }
    public String setPrice(String price){
        return this.price = price;
    }
}
