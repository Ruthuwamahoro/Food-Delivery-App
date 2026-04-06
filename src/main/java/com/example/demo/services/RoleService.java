package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.RoleRepository;
import com.example.demo.model.RoleModel;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public  RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    };

    public RoleModel CreateRole(RoleModel data){
        RoleModel addRole = roleRepository.save(data);
        return addRole;
    }



}