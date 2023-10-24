package com.example.fakebook.repositories;

import com.example.fakebook.entities.Notifications;
import com.example.fakebook.utils.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications, Long> {
    Page<Notifications> findAll(Pageable pageable);

    Page<Notifications> findAllByStatus(Enums.NotificationStatus status, Pageable pageable);

    Optional<Notifications> findById(Long id);

    Optional<Notifications> findByIdAndStatus(Long id, Enums.NotificationStatus status);

    Page<Notifications> findAllByStatusAndUser_Id(Enums.NotificationStatus status, long userID, Pageable pageable);

    List<Notifications> findAllByStatusAndUser_Id(Enums.NotificationStatus status, Long userID);

    List<Notifications> findAllByUser_IdAndStatusNot(Long userID, Enums.NotificationStatus status);
}
