package com.example.fakebook.restapi;

import com.example.fakebook.dto.FriendshipDto;
import com.example.fakebook.entities.Friendships;
import com.example.fakebook.service.FriendShipService;
import com.example.fakebook.service.MessageResourceService;
import com.example.fakebook.utils.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/friendships")
@RequiredArgsConstructor
public class FriendshipApi {
    final FriendShipService friendShipService;
    final MessageResourceService messageResourceService;

    @GetMapping("/getFriend/{id}")
    public List<FriendshipDto> getFriends(@PathVariable(name = "id") Long id) {
        return friendShipService.getFriendshipsByReceiverId(id);
    }

    @GetMapping("/getFollower/{id}")
    public List<FriendshipDto> getFollowers(@PathVariable(name = "id") Long id) {
        return friendShipService.getFollowerByReceiverId(id);
    }

    @PostMapping("/addFriends")
    public FriendshipDto addFriend(@RequestBody FriendshipDto friendshipDto) {
        Friendships friendships = new Friendships();
        BeanUtils.copyProperties(friendshipDto, friendships);
        friendships.setStatus(Enums.FriendshipStatus.PENDING);
        return new FriendshipDto(friendShipService.changeFriend(friendships));
    }

//    @PostMapping("/unFriends")
//    public FriendshipDto unFriend(@RequestBody FriendshipDto friendshipDto) {
//        Optional<Friendships> optionalFriendships = friendShipService.findBySenderIdAndReceiverID(friendshipDto);
//        if (!optionalFriendships.isPresent()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                    messageResourceService.getMessage("id.not.found"));
//        }
//        Friendships friendships = optionalFriendships.get();
//        friendships.setStatus(Enums.FriendshipStatus.NONE);
//        return new FriendshipDto(friendShipService.save(friendships));
//    }

    @PostMapping("/unFriends")
    public String unFriend(@RequestBody FriendshipDto friendshipDto) {
        Optional<Friendships> optionalFriendships = friendShipService.findBySenderIdAndReceiverID(friendshipDto);
        if (!optionalFriendships.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("id.not.found"));
        }

        friendShipService.deleteFriend(optionalFriendships.get().getId());
        return messageResourceService.getMessage("delete.success");
    }

    @PostMapping("/applyFriends")
    public FriendshipDto applyFriends(@RequestBody FriendshipDto friendshipDto) {
        Optional<Friendships> optionalFriendships = friendShipService.findBySenderIdAndReceiverID(friendshipDto);
        if (!optionalFriendships.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("id.not.found"));
        }
        Friendships friendships = optionalFriendships.get();
        friendships.setStatus(Enums.FriendshipStatus.APPROVED);
        return new FriendshipDto(friendShipService.save(friendships));
    }
}
