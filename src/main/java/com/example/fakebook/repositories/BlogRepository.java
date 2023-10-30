package com.example.fakebook.repositories;

import com.example.fakebook.entities.Blog;
import com.example.fakebook.utils.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    Page<Blog> findAll(Pageable pageable);

    Page<Blog> findAllByStatus(Enums.BlogStatus status, Pageable pageable);

    Optional<Blog> findById(Long id);

    Optional<Blog> findByIdAndStatus(Long id, Enums.BlogStatus status);

    Page<Blog> findAllByUser_id(Long id, Pageable pageable);

    List<Blog> findAllByUser_id(Long id);

    Page<Blog> findAllByUser_idAndStatus(Long id, Enums.BlogStatus status, Pageable pageable);
}
