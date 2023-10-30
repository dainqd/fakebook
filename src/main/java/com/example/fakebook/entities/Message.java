package com.example.fakebook.entities;

import com.example.fakebook.dto.MessageDto;
import com.example.fakebook.entities.basic.BaseEntity;
import com.example.fakebook.utils.Enums;
import lombok.AllArgsConstructor;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "messages")
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Long senderId;
    private Long receiverId;
    @Enumerated(EnumType.STRING)
    private Enums.MessageStatus status = Enums.MessageStatus.UNSEEN;

    public Message(MessageDto messageDto) {
        BeanUtils.copyProperties(messageDto, this);
    }
}
