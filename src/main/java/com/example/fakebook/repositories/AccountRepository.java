package com.example.fakebook.repositories;

import com.example.fakebook.entities.Accounts;
import com.example.fakebook.utils.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, Long> {
    Page<Accounts> findAll(Pageable pageable);

    Page<Accounts> findAllByStatus(Enums.AccountStatus status, Pageable pageable);

    Optional<Accounts> findById(Long id);

    Optional<Accounts> findByIdAndStatus(Long id, Enums.AccountStatus status);

    Optional<Accounts> findByUsername(String username);

    Optional<Accounts> findByUsernameAndStatus(String username, Enums.AccountStatus status);

    Optional<Accounts> findByEmail(String email);

    Optional<Accounts> findByEmailAndStatus(String email, Enums.AccountStatus status);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
