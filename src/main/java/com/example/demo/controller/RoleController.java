package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.RoleModel;
import com.example.demo.services.AuthService;
import com.example.demo.services.RoleService;
import com.example.demo.utils.SendResponse;

@RestController
@RequestMapping(value = "/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SendResponse<RoleModel>> addRole(@RequestBody RoleModel role, 
        @RequestHeader(value = "Authorization", required=true) String authHeader
    ){
        

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            SendResponse<RoleModel> response = new SendResponse<>("error", "Unauthorized", null);
            return ResponseEntity.status(401).body(response);
        }

        String token = authHeader.substring(7);

        boolean isAdmin = authService.isAdmin(token);
        System.out.println("+++++++zzz+++++++++ " + isAdmin);

        if(!isAdmin){
            SendResponse<RoleModel> response = new SendResponse<>("error", "Unauthorized", null);
            return ResponseEntity.status(401).body(response);
        }
        RoleModel roles = roleService.CreateRole(role);
        if(roles == null ){
            SendResponse<RoleModel> response = new SendResponse<RoleModel>("error", "role already exists", null);
            return  ResponseEntity.status(409).body(response);
        }
        SendResponse<RoleModel> response = new SendResponse("success", "Role Created Successfully", null);
        return ResponseEntity.status(200).body(response);
    }

}
