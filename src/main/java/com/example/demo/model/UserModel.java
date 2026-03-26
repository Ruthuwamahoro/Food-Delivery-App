package com.example.demo.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Document(collection="users")
@Getter
@Setter
@ToString
public class UserModel {
    @Id
    public String id;
    public String fullName;
    public String email;

    public void generateId(){
        if(this.id == null){
            this.id = UUID.randomUUID().toString();
        }
    }

    public String getFullName(){
        return fullName;
    }

    public String getEmail(){
        return email;
    }

    public String setFullName(String fullName){
        return this.fullName = fullName;
    }

    public String setEmail(String email){
        return this.email = email;
    }
    @Override
    public String toString() {
        return "UserModel{id='" + id + "', fullName='" + fullName + "', email='" + email + "'}";
    }
    
    
}
