package com.example.fakebook.restapi;

import com.example.fakebook.dto.FriendshipDto;
import com.example.fakebook.service.FriendShipService;
import com.example.fakebook.utils.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/friendships")
@RequiredArgsConstructor
public class FriendshipApi {
    final FriendShipService friendShipService;


    @GetMapping("/getFriends/{id}")
    public Page<FriendshipDto> getFriends(@PathVariable("id") long id) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        Page<FriendshipDto> friendshipDtos = friendShipService.findAllByReceiverIdAndStatus(id, Enums.FriendshipStatus.APPROVED, pageable).map(FriendshipDto::new);
        return friendshipDtos;
    }
}
