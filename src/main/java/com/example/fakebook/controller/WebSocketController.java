package com.example.fakebook.controller;

import com.example.fakebook.dto.MessageDto;
import com.example.fakebook.dto.NotificationDto;
import com.example.fakebook.entities.Message;
import com.example.fakebook.service.MessageService;
import com.example.fakebook.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebSocketController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private NotificationService notificationService;

    @MessageMapping("/chat.sendMessage/{senderId}/{receiverId}")
    @SendTo("/topic/publicChatRoom/{senderId}/{receiverId}")
    public List<MessageDto> sendMessage(@DestinationVariable Long senderId, @DestinationVariable Long receiverId,@Payload MessageDto chat) {
        Message message = new Message(chat);
        Message savedMessage = messageService.save(message);
        List<MessageDto> messageDtos = messageService.getFriendshipsByReceiverId(savedMessage.getSenderId(), savedMessage.getReceiverId());
        if (messageDtos.isEmpty()) {
            List<MessageDto> fallbackList = new ArrayList<>();
            fallbackList.add(chat);
            return fallbackList;
        }
        System.out.println(messageDtos);
        return messageDtos;
    }

    @MessageMapping("/chat.loadMessageHistory/{senderId}/{receiverId}")
    @SendTo("/topic/publicChatRoom/{senderId}/{receiverId}")
    public List<MessageDto> loadMessageHistory(@DestinationVariable Long senderId, @DestinationVariable Long receiverId, @Payload MessageDto chat) {
        List<MessageDto> messageDtos = messageService.getFriendshipsByReceiverId(chat.getSenderId(), chat.getReceiverId());
        if (messageDtos.isEmpty()) {
            List<MessageDto> fallbackList = new ArrayList<>();
            fallbackList.add(chat);
            return fallbackList;
        }
        return messageDtos;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/publicChatRoom")
    public MessageDto addUser(@Payload MessageDto chat, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chat.getSender());
        headerAccessor.getSessionAttributes().put("sender", chat.getSenderId());
        headerAccessor.getSessionAttributes().put("receiver", chat.getSenderId());
        return chat;
    }

    @MessageMapping("/notify.getAllNotification/{receiverId}")
    @SendTo("/topic/publicNotification/{receiverId}")
    public List<NotificationDto> getNotifications(@DestinationVariable Long receiverId) {
        List<NotificationDto> notificationsList = notificationService.findAllByUser_IdAndNoDeleted(receiverId);
        return notificationsList;
    }
}

