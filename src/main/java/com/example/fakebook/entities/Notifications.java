package com.example.fakebook.entities;

import com.example.fakebook.dto.NotificationDto;
import com.example.fakebook.entities.basic.BaseEntity;
import com.example.fakebook.utils.Enums;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "notifications")
public class Notifications extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Accounts user;

    private String content;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Enums.NotificationStatus status = Enums.NotificationStatus.UNSEEN;

    public Notifications(NotificationDto notificationDto) {
        BeanUtils.copyProperties(notificationDto, this);
    }
}
