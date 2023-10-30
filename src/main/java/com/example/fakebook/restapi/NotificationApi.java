package com.example.fakebook.restapi;

import com.example.fakebook.dto.NotificationDto;
import com.example.fakebook.entities.Notifications;
import com.example.fakebook.service.MessageResourceService;
import com.example.fakebook.service.NotificationService;
import com.example.fakebook.utils.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationApi {
    final NotificationService notificationService;
    final MessageResourceService messageResourceService;

    @PostMapping("")
    public NotificationDto create(@RequestBody NotificationDto notificationDto) {
        return new NotificationDto(notificationService.create(notificationDto));
    }

    @PostMapping("change-status/{id}")
    public NotificationDto create(@PathVariable("id") long id) {
        Optional<Notifications> notificationsOptional = notificationService.findById(id);
        if (!notificationsOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("id.not.found"));
        }
        Notifications notifications = notificationsOptional.get();
        notifications.setStatus(Enums.NotificationStatus.SEEN);
        return new NotificationDto(notificationService.save(notifications));
    }
}
