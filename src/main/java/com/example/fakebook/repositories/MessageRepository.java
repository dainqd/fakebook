package com.example.fakebook.repositories;

import com.example.fakebook.entities.Blog;
import com.example.fakebook.entities.Message;
import com.example.fakebook.utils.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findAll(Pageable pageable);

    Page<Message> findAllByStatus(Enums.MessageStatus status, Pageable pageable);

    Optional<Message> findById(Long id);

    Optional<Message> findByIdAndStatus(Long id, Enums.MessageStatus status);
}
