package com.example.fakebook.controller;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
@Slf4j
public class HomeController {
    @GetMapping("")
    public String getHomepage() {
        return "v1/views/index";
    }
}
