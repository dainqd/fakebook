package com.example.fakebook.restapi;

import com.example.fakebook.dto.BlogDto;
import com.example.fakebook.dto.MessageDto;
import com.example.fakebook.entities.Message;
import com.example.fakebook.service.MessageResourceService;
import com.example.fakebook.service.MessageService;
import com.example.fakebook.utils.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
public class MessageApi {
    final MessageService messageService;
    final MessageResourceService messageResourceService;

    @GetMapping("/all")
    public Page<MessageDto> getListMessage(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                           @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                           @RequestParam(value = "status", required = false, defaultValue = "") Enums.MessageStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (status != null) {
            return messageService.findAllByStatus(status, pageable).map(MessageDto::new);
        }
        return messageService.findAll(pageable).map(MessageDto::new);
    }

    @GetMapping("/receiverID")
    public Page<MessageDto> getListByReceiverId(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                    @RequestParam(value = "receiverID", required = false, defaultValue = "") Long receiverID) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return messageService.findAllByReceiverId(receiverID, pageable).map(MessageDto::new);
    }

    @GetMapping("/senderID")
    public Page<MessageDto> getListBySenderId(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                                @RequestParam(value = "senderID", required = false, defaultValue = "") Long senderID) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return messageService.findAllBySenderId(senderID, pageable).map(MessageDto::new);
    }

    @PostMapping("")
    public MessageDto sendMessage(@RequestBody MessageDto messageDto) {
        return new MessageDto(messageService.create(messageDto));
    }
}
