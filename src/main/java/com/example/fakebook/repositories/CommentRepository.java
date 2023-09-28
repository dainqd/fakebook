package com.example.fakebook.repositories;

import com.example.fakebook.entities.Comments;
import com.example.fakebook.utils.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface CommentRepository extends JpaRepository<Comments, Long> {
    Page<Comments> findAll(Pageable pageable);

    Page<Comments> findAllByStatus(Enums.CommentStatus status, Pageable pageable);

    Optional<Comments> findById(Long id);

    Optional<Comments> findByIdAndStatus(Long id, Enums.CommentStatus status);
}
