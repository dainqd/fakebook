package com.example.fakebook.config.websocket;

import com.example.fakebook.entities.Message;
import com.example.fakebook.utils.Enums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        Long senderId = (Long) headerAccessor.getSessionAttributes().get("senderId");
        Long receiverId = (Long) headerAccessor.getSessionAttributes().get("receiverId");
        Long room = (Long) headerAccessor.getSessionAttributes().get("room");

        if (username != null) {
            logger.info("User Disconnected : " + username);

            Message chat = new Message();
            chat.setStatus(Enums.MessageStatus.LEAVE);
            chat.setSenderId(senderId);
            chat.setSenderId(receiverId);

            messagingTemplate.convertAndSend("/topic/publicChatRoom/" + room, chat);
        }
    }
}
