package com.example.fakebook.repositories;

import com.example.fakebook.entities.Marketing;
import com.example.fakebook.utils.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarketingRepository  extends JpaRepository<Marketing, Long> {
    Page<Marketing> findAll(Pageable pageable);

    Page<Marketing> findAllByStatus(Enums.MarketingStatus status, Pageable pageable);

    Optional<Marketing> findById(Long id);

    Optional<Marketing> findByIdAndStatus(Long id, Enums.MarketingStatus status);
}
