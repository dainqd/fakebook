package com.example.fakebook.controller;

import com.example.fakebook.dto.BlogDto;
import com.example.fakebook.entities.Blog;
import com.example.fakebook.service.BlogService;
import com.example.fakebook.service.MessageResourceService;
import com.example.fakebook.utils.Enums;
import com.example.fakebook.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("blog")
@Slf4j
public class BlogController {
    final BlogService blogService;
    final MessageResourceService messageResourceService;

    @GetMapping("")
    public String getAllByUser(Model model,
                               HttpServletRequest request,
                               @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                               @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userId")) {
                    String value = cookie.getValue();
                    long id = Long.parseLong(value);
                    Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
                    Page<BlogDto> blogDtos = blogService.findAllByUserIdAndStatus(id, pageable).map(BlogDto::new);
                    model.addAttribute("blogDtos", blogDtos);
                    return "v1/views/blog";
                }
            }
        }
        return "error/404";
    }
}
