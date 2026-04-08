// package com.example.demo.model;

// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;

// import lombok.Data;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
// import lombok.ToString;

// @NoArgsConstructor
// @Document(collection="roles")
// @Data
// @Setter
// @Getter
// @ToString
// public class RoleModel {

//     @Id
//     public String id;

//     public String name;
//     public String description;

//     public String getId() { return id; }
//     public void setId(String id) { this.id = id; }

//     public String getName(){
//         return name;
//     }

//     public String getDescription(){
//         return description;
//     }


//     public String setName(String Name){
//         return this.name= Name;
//     }

//     public String setDescription(String description){
//         return this.description = description;
//     }

    
    
// }


package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Document(collection = "roles")
@Data  
public class RoleModel {

    @Id
    private String id;
    private String name;
    private String description;
}
