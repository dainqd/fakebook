package com.example.fakebook.controller;

import com.example.fakebook.dto.FriendshipDto;
import com.example.fakebook.dto.MessageDto;
import com.example.fakebook.entities.Message;
import com.example.fakebook.service.FriendShipService;
import com.example.fakebook.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageService messageService;
    @Autowired
    private FriendShipService friendShipService;


//    @MessageMapping("/chat")
//    @SendTo("/topic/messages")
//    public void sendMessage(MessageDto messageDto) {
//        Message message = new Message(messageDto);
//        Message savedMessage = messageService.save(message);
//        messagingTemplate.convertAndSend("/topic/messages", savedMessage);
//    }
//
//    @MessageMapping("/chat/{senderId}/{receiverId}")
//    @SendTo("/topic/messages/{senderId}/{receiverId}")
//    public List<MessageDto> getAllMessage(@DestinationVariable Long senderId, @DestinationVariable Long receiverId) {
//        List<MessageDto> messageDtos = messageService.getFriendshipsByReceiverId(senderId, receiverId);
//        return messageDtos;
//    }
//
//    @MessageMapping("/getFriends/{receiverId}")
//    @SendTo("/topic/friends/{receiverId}")
//    public List<FriendshipDto> getFriendships(@DestinationVariable Long receiverId) {
//        List<FriendshipDto> friendships = friendShipService.getFriendshipsByReceiverId(receiverId);
//        return friendships;
//    }
//
//    @MessageMapping("/getFollower/{receiverId}")
//    @SendTo("/topic/follower/{receiverId}")
//    public List<FriendshipDto> getgetFollowers(@DestinationVariable Long receiverId) {
//        List<FriendshipDto> followers = friendShipService.getFollowerByReceiverId(receiverId);
//        return followers;
//    }

    @MessageMapping("/chat.sendMessage/{id}")
    @SendTo("/topic/publicChatRoom/{id}")
    public List<MessageDto> sendMessage(@DestinationVariable("id") Long id, @Payload MessageDto chat) {
        Message message = new Message(chat);
        Message savedMessage = messageService.save(message);
        List<MessageDto> messageDtos = messageService.getFriendshipsByReceiverId(chat.getSenderId(), chat.getReceiverId());
        return messageDtos;
    }

    @MessageMapping("/chat.addUser/{id}")
    @SendTo("/topic/publicChatRoom/{id}")
    public Message addUser(@DestinationVariable("id") Long id, @Payload Message chat, SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chat.getSenderId());
        headerAccessor.getSessionAttributes().put("username", chat.getSenderId());
        headerAccessor.getSessionAttributes().put("username", chat.getSenderId());
        headerAccessor.getSessionAttributes().put("room", id);
        return chat;
    }
}

