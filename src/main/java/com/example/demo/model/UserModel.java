// package com.example.demo.model;

// import java.time.LocalDateTime;
// import java.util.UUID;

// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;

// import lombok.Data;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
// import lombok.ToString;

// @NoArgsConstructor
// @Document(collection="users")
// @Data
// @Getter
// @Setter
// @ToString
// public class UserModel {
//     @Id
//     public String id;
//     public String fullName;
//     public String email;
//     public String picture;

//     public String roleId;
//     public LocalDateTime createdAt;
//     public LocalDateTime updatedAt;

//     public void generateId(){
//         if(this.id == null){
//             this.id = UUID.randomUUID().toString();
//         }
//     }

//     // public String getFullName(){
//     //     return fullName;
//     // }

//     // public String getEmail(){
//     //     return email;
//     // }
//     // public String getId(){
//     //     return id;
//     // }

//     // public String setFullName(String fullName){
//     //     return this.fullName = fullName;
//     // }

//     // public String setEmail(String email){
//     //     return this.email = email;
//     // }

//     public void setRoleId(String roleId){
//         this.roleId = roleId;
//     }
//     public LocalDateTime setCreatedAt(){
//         return this.createdAt = LocalDateTime.now();
//     }
//     public LocalDateTime setUpdatedAt(){
//         return this.updatedAt = LocalDateTime.now();
//     }
//     // @Override
//     // public String toString() {
//     //     return "UserModel{id='" + id + "', fullName='" + fullName + "', email='" + email + "'}";
//     // }
    
    
// }




package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Document(collection = "users")
@Data  // @Data already generates all getters/setters — remove duplicate @Getter @Setter @ToString
public class UserModel {

    @Id
    private String id;        // ← use private with @Data, not public
    private String fullName;
    private String email;
    private String picture;
    private String roleId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}
