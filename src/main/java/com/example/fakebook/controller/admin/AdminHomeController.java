package com.example.fakebook.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin")
@Slf4j
public class AdminHomeController {
    @GetMapping("")
    public String getHomepage() {
        return "v1/admin/admin";
    }
}
