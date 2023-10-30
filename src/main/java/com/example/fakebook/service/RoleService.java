package com.example.fakebook.service;

import com.example.fakebook.entities.Roles;
import com.example.fakebook.utils.Enums;
import com.example.fakebook.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Optional<Roles> findByName(Enums.Roles name) {
        return roleRepository.findByName(name);
    }

    public List<Roles> saveAll(List<Roles> roles) {
        return roleRepository.saveAll(roles);
    }
}
