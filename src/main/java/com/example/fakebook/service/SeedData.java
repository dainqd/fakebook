package com.example.fakebook.service;

import com.example.fakebook.entities.Roles;
import com.example.fakebook.repositories.RoleRepository;
import com.example.fakebook.utils.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeedData {
     final RoleService roleService;

    public void RoleData() {
        List<Roles> rolesList = new ArrayList<>();
        Roles roles = new Roles(Enums.Roles.USER);
        rolesList.add(roles);
        roles = new Roles(Enums.Roles.ADMIN);
        rolesList.add(roles);
        roleService.saveAll(rolesList);
    }
}
