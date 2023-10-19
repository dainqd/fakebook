package com.example.fakebook.restapi;

import com.example.fakebook.dto.BlogDto;
import com.example.fakebook.entities.Accounts;
import com.example.fakebook.entities.Blog;
import com.example.fakebook.service.BlogService;
import com.example.fakebook.service.MessageResourceService;
import com.example.fakebook.service.UserService;
import com.example.fakebook.utils.Enums;
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
@RequestMapping("/api/v1/blogs")
@RequiredArgsConstructor
public class BlogApi {
    final UserService userService;
    final BlogService blogService;
    final MessageResourceService messageResourceService;

    @GetMapping("")
    public Page<BlogDto> getList(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                 @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return blogService.findAllByStatus(Enums.BlogStatus.ACTIVE, pageable).map(BlogDto::new);
    }

    @GetMapping("/user/{id}")
    public Page<BlogDto> getAllByUser(@PathVariable(name = "id") Long id,
                                      @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                      @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return blogService.findAllByUserId(id, pageable).map(BlogDto::new);
    }

    @GetMapping("{id}")
    public BlogDto getDetail(@PathVariable(name = "id") Long id) {
        Optional<Blog> optionalBlog;
        optionalBlog = blogService.findByIdAndStatus(id, Enums.BlogStatus.ACTIVE);
        if (!optionalBlog.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("account.not.found"));
        }
        Blog blog = optionalBlog.get();
        blog.setViews(blog.getViews() + 1);
        blog = blogService.save(blog);
        return new BlogDto(blog);
    }

    @PostMapping("")
    public BlogDto create(@RequestBody BlogDto blogDto) {
        return new BlogDto(blogService.create(blogDto, blogDto.getUser().getId()));
    }

    @PutMapping("")
    public String update(@RequestBody BlogDto request) {
        blogService.update(request, request.getUser().getId());
        return messageResourceService.getMessage("update.success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        Optional<Blog> optionalBlog;
        optionalBlog = blogService.findByIdAndStatus(id, Enums.BlogStatus.ACTIVE);
        if (!optionalBlog.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("account.not.found"));
        }
        blogService.deleteById(id, optionalBlog.get().getUser().getId());
        return new ResponseEntity<>(messageResourceService.getMessage("delete.success"), HttpStatus.OK);
    }

    @PostMapping("{id}")
    public BlogDto likeBlog(@PathVariable(name = "id") Long id, @RequestParam(value = "check", required = false, defaultValue = "1") int check) {
        Blog blog = blogService.likeBlog(id, check);
        return new BlogDto(blog);
    }

    @PostMapping("view/{id}")
    public BlogDto viewBlog(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.viewBlog(id);
        return new BlogDto(blog);
    }
}
