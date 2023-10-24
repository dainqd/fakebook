package com.example.fakebook.service;

import com.example.fakebook.dto.MessageDto;
import com.example.fakebook.dto.NotificationDto;
import com.example.fakebook.entities.Accounts;
import com.example.fakebook.entities.Notifications;
import com.example.fakebook.repositories.NotificationRepository;
import com.example.fakebook.utils.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    final MessageResourceService messageResourceService;
    final NotificationRepository notificationRepository;
    final UserService userService;

    public Page<Notifications> findAll(Pageable pageable) {
        return notificationRepository.findAll(pageable);
    }

    public Optional<Notifications> findById(long id) {
        return notificationRepository.findById(id);
    }

    public Notifications save(Notifications notifications) {
        return notificationRepository.save(notifications);
    }

    public Notifications create(NotificationDto notificationDto, long adminId) {
        try {
            Notifications notifications = new Notifications();

            BeanUtils.copyProperties(notificationDto, notifications);

            Optional<Accounts> optionalAccounts = userService.findById(notificationDto.getUser().getId());
            if (!optionalAccounts.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageResourceService.getMessage("id.not.found"));
            }
            notifications.setUser(optionalAccounts.get());

            notifications.setCreatedAt(LocalDateTime.now());
            notifications.setCreatedBy(adminId);
            notifications.setStatus(Enums.NotificationStatus.UNSEEN);

            return notificationRepository.save(notifications);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("create.error"));

        }
    }

    public Notifications update(NotificationDto notificationDto, long adminID) {
        try {
            Optional<Notifications> notificationsOptional = notificationRepository.findById(notificationDto.getId());
            if (!notificationsOptional.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageResourceService.getMessage("id.not.found"));
            }
            Notifications notifications = notificationsOptional.get();

            BeanUtils.copyProperties(notificationDto, notifications);

            Optional<Accounts> optionalAccounts = userService.findById(notificationDto.getUser().getId());
            if (!optionalAccounts.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageResourceService.getMessage("id.not.found"));
            }
            notifications.setUser(optionalAccounts.get());

            notifications.setUpdatedAt(LocalDateTime.now());
            notifications.setUpdatedBy(adminID);

            return notificationRepository.save(notifications);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("update.error"));
        }
    }

    public Notifications changeStatus(NotificationDto notificationDto, long adminID) {
        try {
            Optional<Notifications> notificationsOptional = notificationRepository.findById(notificationDto.getId());
            if (!notificationsOptional.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageResourceService.getMessage("id.not.found"));
            }
            Notifications notifications = notificationsOptional.get();
            notifications.setStatus(notificationDto.getStatus());

            return notificationRepository.save(notifications);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("update.error"));
        }
    }

    public void deleteById(long id, long adminID) {
        try {
            Optional<Notifications> notificationsOptional = notificationRepository.findById(id);
            if (!notificationsOptional.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageResourceService.getMessage("id.not.found"));
            }
            notificationsOptional.get().setStatus(Enums.NotificationStatus.DELETED);
            notificationsOptional.get().setDeletedAt(LocalDateTime.now());
            notificationsOptional.get().setDeletedBy(adminID);
            notificationRepository.save(notificationsOptional.get());
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("cancel.error"));
        }
    }

    public Page<Notifications> findAllByStatus(Enums.NotificationStatus status, Pageable pageable) {
        return notificationRepository.findAllByStatus(status, pageable);
    }

    public Optional<Notifications> findByIdAndStatus(long id, Enums.NotificationStatus status) {
        return notificationRepository.findByIdAndStatus(id, status);
    }

    public List<NotificationDto> findAllByUser_IdAndNoDeleted(long id) {
        List<Notifications> notificationsList = notificationRepository.findAllByUser_IdAndStatusNot(id, Enums.NotificationStatus.DELETED);
        return notificationsList.stream().map(NotificationDto::new).collect(Collectors.toList());
    }
}
