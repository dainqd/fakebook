package com.example.fakebook.service;

import com.example.fakebook.dto.FriendshipDto;
import com.example.fakebook.entities.Friendships;
import com.example.fakebook.repositories.FriendShipRepository;
import com.example.fakebook.utils.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendShipService {
    final FriendShipRepository friendShipRepository;
    final MessageResourceService messageResourceService;

    public Page<Friendships> findAll(Pageable pageable) {
        return friendShipRepository.findAll(pageable);
    }

    public Optional<Friendships> findById(long id) {
        return friendShipRepository.findById(id);
    }

    public Friendships save(Friendships friendships) {
        return friendShipRepository.save(friendships);
    }

    public Friendships create(FriendshipDto friendshipDto, long adminId) {
        try {
            Friendships friendships = new Friendships();
            BeanUtils.copyProperties(friendshipDto, friendships);
            friendships.setCreatedAt(LocalDateTime.now());
            friendships.setCreatedBy(adminId);

            return friendShipRepository.save(friendships);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("create.error"));

        }
    }

    public Friendships changeFriend(Friendships friendships) {
        return friendShipRepository.save(friendships);
    }

    public Friendships update(long id, Enums.FriendshipStatus status) {
        try {
            Optional<Friendships> optionalFriendships = friendShipRepository.findById(id);
            if (!optionalFriendships.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageResourceService.getMessage("id.not.found"));
            }
            Friendships friendships = optionalFriendships.get();

            friendships.setUpdatedAt(LocalDateTime.now());
            friendships.setStatus(status);
            return friendShipRepository.save(friendships);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("update.error"));
        }
    }

    public void deleteById(long id, long adminID) {
        try {
            Optional<Friendships> optionalFriendships = friendShipRepository.findById(id);
            if (!optionalFriendships.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageResourceService.getMessage("id.not.found"));
            }
            optionalFriendships.get().setStatus(Enums.FriendshipStatus.DELETED);
            optionalFriendships.get().setDeletedAt(LocalDateTime.now());
            optionalFriendships.get().setDeletedBy(adminID);
            friendShipRepository.save(optionalFriendships.get());
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("cancel.error"));
        }
    }

    public Page<Friendships> findAllByStatus(Enums.FriendshipStatus status, Pageable pageable) {
        return friendShipRepository.findAllByStatus(status, pageable);
    }

    public Optional<Friendships> findByIdAndStatus(long id, Enums.FriendshipStatus status) {
        return friendShipRepository.findByIdAndStatus(id, status);
    }

    public Optional<Friendships> findBySenderIdAndReceiverId(long sender, long receiver) {
        return friendShipRepository.findBySenderIdAndReceiverId(sender, receiver);
    }

//    public Page<Friendships> findAllByReceiverId(long receiverID, Pageable pageable) {
//        return friendShipRepository.findByReceiverIdAndStatus(receiverID, pageable);
//    }

    public Page<Friendships> findAllByReceiverIdAndStatus(long receiverID, Enums.FriendshipStatus status, Pageable pageable) {
        return friendShipRepository.findByReceiverIdAndStatus(receiverID, status, pageable);
    }

    public Page<Friendships> findAllByReceiverIdAndStatus(FriendshipDto friendshipDto, Pageable pageable) {
        return friendShipRepository.findByReceiverIdAndStatus(friendshipDto.getReceiver().getId(), Enums.FriendshipStatus.valueOf(friendshipDto.getStatus()), pageable);
    }

//    public Page<Friendships> findAllBySenderId(long senderID, Pageable pageable) {
//        return friendShipRepository.findBySenderIdAndStatus(senderID, pageable);
//    }

    public Page<Friendships> findAllBySenderIdAndStatus(long senderID, Enums.FriendshipStatus status, Pageable pageable) {
        return friendShipRepository.findBySenderIdAndStatus(senderID, status, pageable);
    }

//    public Page<Friendships> findAllBySenderIdAndReceiverId(long senderID, long receiverID, Pageable pageable) {
//        return friendShipRepository.findAllBySenderIdAndReceiverId(senderID, receiverID, pageable);
//    }

    public List<FriendshipDto> getFriendshipsByReceiverId(Long receiverId) {
        List<Friendships> friendships = friendShipRepository.findByReceiverIdAndStatusOrderBy(receiverId, Enums.FriendshipStatus.APPROVED);
        return friendships.stream().map(FriendshipDto::new).collect(Collectors.toList());
    }

    public List<FriendshipDto> getFollowerByReceiverId(Long receiverId) {
        List<Friendships> friendships = friendShipRepository.findByReceiverIdAndStatus(receiverId, Enums.FriendshipStatus.PENDING);
        return friendships.stream().map(FriendshipDto::new).collect(Collectors.toList());
    }
}
