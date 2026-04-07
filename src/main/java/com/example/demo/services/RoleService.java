package com.example.demo.services;

import java.util.Optional;

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
        if(data.getName() == null || data.getName().isEmpty()){
            return null;
        }
        Optional<RoleModel> isRoleExists = roleRepository.findByName(data.getName());
        if(isRoleExists.isPresent()){
            return null;
        }
        RoleModel addRole = roleRepository.save(data);
        return addRole;
    }



}