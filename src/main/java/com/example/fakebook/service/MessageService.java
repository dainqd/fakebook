package com.example.fakebook.service;

import com.example.fakebook.dto.FriendshipDto;
import com.example.fakebook.dto.MessageDto;
import com.example.fakebook.entities.Friendships;
import com.example.fakebook.entities.Message;
import com.example.fakebook.repositories.MessageRepository;
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
public class MessageService {
    final MessageRepository messageRepository;
    final MessageResourceService messageResourceService;

    public Page<Message> findAll(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

    public Optional<Message> findById(long id) {
        return messageRepository.findById(id);
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public Message create(MessageDto messageDto, long adminId) {
        try {
            Message message = new Message();
            BeanUtils.copyProperties(messageDto, message);
            message.setCreatedAt(LocalDateTime.now());
            message.setCreatedBy(adminId);

            return messageRepository.save(message);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("create.error"));

        }
    }

    public Message update(MessageDto messageDto, long adminID) {
        try {
            Optional<Message> optionalMessage = messageRepository.findById(messageDto.getId());
            if (!optionalMessage.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageResourceService.getMessage("id.not.found"));
            }
            Message message = optionalMessage.get();

            BeanUtils.copyProperties(messageDto, message);
            message.setUpdatedAt(LocalDateTime.now());
            message.setUpdatedBy(adminID);
            return messageRepository.save(message);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("update.error"));
        }
    }

    public void deleteById(long id, long adminID) {
        try {
            Optional<Message> optionalMessage = messageRepository.findById(id);
            if (!optionalMessage.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageResourceService.getMessage("id.not.found"));
            }
            optionalMessage.get().setStatus(Enums.MessageStatus.DELETED);
            optionalMessage.get().setDeletedAt(LocalDateTime.now());
            optionalMessage.get().setDeletedBy(adminID);
            messageRepository.save(optionalMessage.get());
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("cancel.error"));
        }
    }

    public Page<Message> findAllByStatus(Enums.MessageStatus status, Pageable pageable) {
        return messageRepository.findAllByStatus(status, pageable);
    }

    public Optional<Message> findByIdAndStatus(long id, Enums.MessageStatus status) {
        return messageRepository.findByIdAndStatus(id, status);
    }

    public Page<Message> findAllByReceiverId(long receiverID, Pageable pageable) {
        return messageRepository.findAllByReceiverId(receiverID, pageable);
    }

    public Page<Message> findAllByReceiverIdAndStatus(long receiverID, Enums.MessageStatus status, Pageable pageable) {
        return messageRepository.findAllByReceiverIdAndStatus(receiverID, status, pageable);
    }

    public Page<Message> findAllBySenderId(long senderID, Pageable pageable) {
        return messageRepository.findAllBySenderId(senderID, pageable);
    }

    public Page<Message> findAllBySenderIdAndStatus(long senderID, Enums.MessageStatus status, Pageable pageable) {
        return messageRepository.findAllBySenderIdAndStatus(senderID, status, pageable);
    }

    public Page<Message> findAllBySenderIdAndReceiverId(long senderID, long receiverID, Pageable pageable) {
        return messageRepository.findAllBySenderIdAndReceiverId(senderID, receiverID, pageable);
    }

    public List<MessageDto> getFriendshipsByReceiverId(Long senderId, Long receiverId) {
        List<Message> messages = messageRepository.findAllBySenderIdAndReceiverIdOrderBy(senderId, receiverId);
        return messages.stream().map(MessageDto::new).collect(Collectors.toList());
    }
}
