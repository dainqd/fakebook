package com.example.fakebook.controller;

import com.example.fakebook.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("message")
@Slf4j
public class MessageController {
    final MessageService messageService;

    @GetMapping("")
    public String getMessage(Model model,
                             @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                             @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return "v1/views/message";
    }
}
