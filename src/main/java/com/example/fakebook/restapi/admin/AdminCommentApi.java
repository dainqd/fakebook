package com.example.fakebook.restapi.admin;

import com.example.fakebook.dto.CommentDto;
import com.example.fakebook.entities.Accounts;
import com.example.fakebook.entities.Comments;
import com.example.fakebook.service.CommentService;
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
@RequestMapping("admin/api/comments")
public class AdminCommentApi {
    final UserService userService;
    final CommentService commentService;
    final MessageResourceService messageResourceService;

    @GetMapping("")
    public Page<CommentDto> getList(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                    @RequestParam(value = "status", required = false, defaultValue = "") Enums.CommentStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (status != null) {
            return commentService.findAllByStatus(status, pageable).map(CommentDto::new);
        }
        return commentService.findAll(pageable).map(CommentDto::new);
    }

    @GetMapping("{id}/{status}")
    public CommentDto getDetail(@PathVariable(name = "id") Long id, @PathVariable(name = "status") Enums.CommentStatus status) {
        Optional<Comments> optionalComments;
        if (status != null) {
            optionalComments = commentService.findByIdAndStatus(id, status);
        } else {
            optionalComments = commentService.findById(id);
        }
        if (!optionalComments.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        return new CommentDto(optionalComments.get());
    }


    @PostMapping("")
    public CommentDto create(@RequestBody CommentDto commentDto) {
        String username = Utils.getUsername();
        Optional<Accounts> optionalAccounts = userService.findByUsername(username);
        if (!optionalAccounts.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        Accounts accounts = optionalAccounts.get();
        return new CommentDto(commentService.create(commentDto, accounts.getId()));
    }

    @PutMapping("")
    public String update(@RequestBody CommentDto request) {
        String username = Utils.getUsername();
        Optional<Accounts> optionalAccounts = userService.findByUsername(username);
        if (!optionalAccounts.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        Accounts accounts = optionalAccounts.get();
        commentService.update(request, accounts.getId());
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
        commentService.deleteById(id, accounts.getId());
        return new ResponseEntity<>(messageResourceService.getMessage("delete.success"), HttpStatus.OK);
    }
}
