package com.example.fakebook.restapi.admin;

import com.example.fakebook.dto.BlogDto;
import com.example.fakebook.entities.Accounts;
import com.example.fakebook.entities.Blog;
import com.example.fakebook.service.BlogService;
import com.example.fakebook.service.MessageResourceService;
import com.example.fakebook.service.UserService;
import com.example.fakebook.utils.Enums;
import com.example.fakebook.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/api/blogs")
public class AdminBlogApi {
    final UserService userService;
    final BlogService blogService;
    final MessageResourceService messageResourceService;

    @GetMapping("")
    public Page<BlogDto> getList(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                 @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                 @RequestParam(value = "status", required = false, defaultValue = "") Enums.BlogStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (status != null) {
            return blogService.findAllByStatus(status, pageable).map(BlogDto::new);
        }
        return blogService.findAll(pageable).map(BlogDto::new);
    }

    @GetMapping("{id}/{status}")
    public BlogDto getDetail(@PathVariable(name = "id") Long id, @PathVariable(name = "status") Enums.BlogStatus status) {
        Optional<Blog> optionalBlog;
        if (status != null) {
            optionalBlog = blogService.findByIdAndStatus(id, status);
        } else {
            optionalBlog = blogService.findById(id);
        }
        if (!optionalBlog.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        return new BlogDto(optionalBlog.get());
    }


    @PostMapping("")
    public BlogDto create(@RequestBody BlogDto blogDto) {
        String username = Utils.getUsername();
        Optional<Accounts> optionalAccounts = userService.findByUsername(username);
        if (!optionalAccounts.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        Accounts accounts = optionalAccounts.get();
        return new BlogDto(blogService.create(blogDto, accounts.getId()));
    }

    @PutMapping("")
    public String update(@RequestBody BlogDto request) {
        String username = Utils.getUsername();
        Optional<Accounts> optionalAccounts = userService.findByUsername(username);
        if (!optionalAccounts.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        Accounts accounts = optionalAccounts.get();
        blogService.update(request, accounts.getId());
        return messageResourceService.getMessage("update.success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        String username = Utils.getUsername();
        System.out.println(username);
        Optional<Accounts> optionalAccounts = userService.findByUsername(username);
        if (!optionalAccounts.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        Accounts accounts = optionalAccounts.get();
        blogService.deleteById(id, accounts.getId());
        return new ResponseEntity<>(messageResourceService.getMessage("delete.success"), HttpStatus.OK);
    }
}
