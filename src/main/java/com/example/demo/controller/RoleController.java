package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.RoleModel;
import com.example.demo.services.RoleService;
import com.example.demo.utils.SendResponse;

@RestController
@RequestMapping(value="/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SendResponse<RoleModel>> addRole(@RequestBody RoleModel role){
        RoleModel roles = roleService.CreateRole(role);
        if(roles == null ){
            return  ResponseEntity.notFound().build();
        }
        SendResponse<RoleModel> response = new SendResponse(200, "Role Created Successfully", null);
        return ResponseEntity.status(200).body(response);
    }


    
}
