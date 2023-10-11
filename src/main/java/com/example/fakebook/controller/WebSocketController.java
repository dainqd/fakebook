package com.example.fakebook.controller;

import com.example.fakebook.dto.FriendshipDto;
import com.example.fakebook.dto.MessageDto;
import com.example.fakebook.entities.Friendships;
import com.example.fakebook.entities.Message;
import com.example.fakebook.service.FriendShipService;
import com.example.fakebook.service.MessageService;
import com.example.fakebook.utils.Enums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageService messageService;
    @Autowired
    private FriendShipService friendShipService;


    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public void sendMessage(MessageDto messageDto) {
        Message message = new Message(messageDto);
        Message savedMessage = messageService.save(message);
        messagingTemplate.convertAndSend("/topic/messages", savedMessage);
    }

    @MessageMapping("/getFriends/{receiverId}")
    @SendTo("/topic/friends/{receiverId}")
    public List<FriendshipDto> getFriendships(@DestinationVariable Long receiverId) {
        List<FriendshipDto> friendships = friendShipService.getFriendshipsByReceiverId(receiverId);
        return friendships;
    }
}

