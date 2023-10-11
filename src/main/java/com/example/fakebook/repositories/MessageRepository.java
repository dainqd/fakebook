package com.example.fakebook.repositories;

import com.example.fakebook.entities.Blog;
import com.example.fakebook.entities.Message;
import com.example.fakebook.utils.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findAll(Pageable pageable);

    Page<Message> findAllByStatus(Enums.MessageStatus status, Pageable pageable);

    Optional<Message> findById(Long id);

    Optional<Message> findByIdAndStatus(Long id, Enums.MessageStatus status);

    Page<Message> findAllByReceiverId(Long receiverID, Pageable pageable);

    Page<Message> findAllByReceiverIdAndStatus(Long receiverID, Enums.MessageStatus status, Pageable pageable);

    Page<Message> findAllBySenderId(Long senderId, Pageable pageable);

    Page<Message> findAllBySenderIdAndStatus(Long senderId, Enums.MessageStatus status, Pageable pageable);

    Page<Message> findAllBySenderIdAndReceiverId(Long senderId, Long receiverID, Pageable pageable);

    List<Message> findAllBySenderIdAndReceiverId(Long senderId, Long receiverID);


    @Query("SELECT e FROM Message e WHERE (e.senderId = :senderId AND e.receiverId = :receiverId) OR (e.senderId = :receiverId AND e.receiverId = :senderId) ORDER BY e.id")
    List<Message> findAllBySenderIdAndReceiverIdOrderBy(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);
}
