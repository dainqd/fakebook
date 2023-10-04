package com.example.fakebook.controller;

import com.example.fakebook.entities.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/publicChatRoom")
    public Message sendMessage(@Payload Message chat) {
        return chat;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/publicChatRoom")
    public Message addUser(@Payload Message chat, SimpMessageHeaderAccessor headerAccessor) {
        // Add id in web socket session
        headerAccessor.getSessionAttributes().put("username", chat.getSenderId());
        return chat;
    }

}
