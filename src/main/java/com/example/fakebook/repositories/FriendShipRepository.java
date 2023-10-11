package com.example.fakebook.repositories;

import com.example.fakebook.entities.Friendships;
import com.example.fakebook.utils.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendShipRepository extends JpaRepository<Friendships, Long> {
    Page<Friendships> findAll(Pageable pageable);

    Page<Friendships> findAllByStatus(Enums.FriendshipStatus status, Pageable pageable);

    Optional<Friendships> findById(Long id);

    Optional<Friendships> findByIdAndStatus(Long id, Enums.FriendshipStatus status);

    Page<Friendships> findByReceiverIdAndStatus(Long receiverId, Enums.FriendshipStatus status, Pageable pageable);

    Page<Friendships> findBySenderIdAndStatus(Long senderId, Enums.FriendshipStatus status, Pageable pageable);

    Optional<Friendships> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    //    Page<Friendships> findByReceiverIdAndStatus(Long receiverId, Enums.FriendshipStatus status, Pageable pageable);
    List<Friendships> findByReceiverIdAndStatus(Long receiverId, Enums.FriendshipStatus status);
}
