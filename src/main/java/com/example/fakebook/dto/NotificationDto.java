package com.example.fakebook.dto;

import com.example.fakebook.entities.Accounts;
import com.example.fakebook.entities.Message;
import com.example.fakebook.entities.Notifications;
import com.example.fakebook.utils.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private long id;
    private Accounts user;
    private String content;
    private Enums.NotificationStatus status = Enums.NotificationStatus.UNSEEN;
    private LocalDateTime createdAt;

    public NotificationDto(Notifications notifications) {
        BeanUtils.copyProperties(notifications, this);
    }
}
