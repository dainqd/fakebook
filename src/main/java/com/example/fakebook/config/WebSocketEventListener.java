package com.example.fakebook.config;

import com.example.fakebook.entities.Accounts;
import com.example.fakebook.entities.Message;
import com.example.fakebook.service.MessageResourceService;
import com.example.fakebook.service.MessageService;
import com.example.fakebook.service.UserDetailsServiceImpl;
import com.example.fakebook.utils.Enums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Optional;


@Component
public class WebSocketEventListener {
    public static final Logger logger = LoggerFactory.getLogger(com.example.fakebook.config.WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private MessageResourceService messageResourceService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            logger.info("User Disconnected : " + username);

            if (username != null) {
                logger.info("User Disconnected : " + username);

                Message chat = new Message();
                chat.setStatus(Enums.MessageStatus.SEEN);

                Optional<Accounts> optionalAccounts = userDetailsService.findByUsername(username);
                if (!optionalAccounts.isPresent()) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                            messageResourceService.getMessage("id.not.found"));
                }

                chat.setSenderId(optionalAccounts.get().getId());

                messagingTemplate.convertAndSend("/topic/publicChatRoom", chat);
            }
        }
    }
}
