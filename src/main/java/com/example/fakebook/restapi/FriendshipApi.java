package com.example.fakebook.restapi;

import com.example.fakebook.dto.FriendshipDto;
import com.example.fakebook.entities.Friendships;
import com.example.fakebook.service.FriendShipService;
import com.example.fakebook.utils.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/friendships")
@RequiredArgsConstructor
public class FriendshipApi {
    final FriendShipService friendShipService;

    @PostMapping("/addFriends")
    public FriendshipDto addFriend(@RequestBody FriendshipDto friendshipDto) {
        Friendships friendships = new Friendships();
        BeanUtils.copyProperties(friendshipDto, friendships);
        friendships.setStatus(Enums.FriendshipStatus.PENDING);
        return new FriendshipDto(friendShipService.changeFriend(friendships));
    }

//    @PostMapping("/unFriends")
//    public FriendshipDto unFriend(@RequestBody FriendshipDto friendshipDto) {
//        Friendships friendships = new Friendships();
//        BeanUtils.copyProperties(friendshipDto, friendships);
//        friendships.setStatus(Enums.FriendshipStatus.NONE);
//        return new FriendshipDto(friendShipService.changeFriend(friendships));
//    }
//
//    @PostMapping("/applyFriends")
//    public FriendshipDto applyFriends(@RequestBody FriendshipDto friendshipDto) {
//        Friendships friendships = new Friendships();
//        BeanUtils.copyProperties(friendshipDto, friendships);
//        friendships.setStatus(Enums.FriendshipStatus.APPROVED);
//        return new FriendshipDto(friendShipService.changeFriend(friendships));
//    }
}
