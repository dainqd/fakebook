package com.example.fakebook.controller;

import com.example.fakebook.dto.BlogDto;
import com.example.fakebook.service.BlogService;
import com.example.fakebook.utils.Enums;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
@Slf4j
public class HomeController {
    final BlogService blogService;

    @GetMapping("")
    public String getHomepage(Model model,
                              @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                              @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<BlogDto> blogDtos = blogService.findAllByStatus(Enums.BlogStatus.ACTIVE, pageable).map(BlogDto::new);
        model.addAttribute("blogDtos", blogDtos);

        return "v1/views/index";
    }

    @GetMapping("photos")
    public String getPhotoByUser(Model model,
                                 HttpServletRequest request) {

        return "v1/views/photos";
    }
}
