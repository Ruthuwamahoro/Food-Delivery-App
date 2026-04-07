package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Document(collection="roles")
@Setter
@Getter
@ToString
public class RoleModel {

    @Id
    public String id;

    public String name;
    public String description;

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public String setName(String Name){
        return this.name= Name;
    }

    public String setDescription(String description){
        return this.description = description;
    }

    
    
}
