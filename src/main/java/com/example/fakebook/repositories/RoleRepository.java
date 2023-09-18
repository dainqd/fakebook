package com.example.fakebook.repositories;

import com.example.fakebook.entities.Roles;
import com.example.fakebook.utils.Enums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(Enums.Roles name);
}
