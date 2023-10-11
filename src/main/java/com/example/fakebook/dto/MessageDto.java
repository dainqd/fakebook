package com.example.fakebook.dto;

import com.example.fakebook.entities.Accounts;
import com.example.fakebook.entities.Message;
import com.example.fakebook.utils.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Long id;
    private String content;
    private Long senderId;
    private Long receiverId;
    private Enums.MessageStatus status = Enums.MessageStatus.UNSEEN;

    public MessageDto(Message message) {
        BeanUtils.copyProperties(message, this);
    }
}
