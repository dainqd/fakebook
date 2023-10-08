package com.example.fakebook.restapi;

import com.example.fakebook.dto.CommentDto;
import com.example.fakebook.entities.Comments;
import com.example.fakebook.service.CommentService;
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
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentApi {
    final UserService userService;
    final CommentService commentService;
    final MessageResourceService messageResourceService;

    @GetMapping("")
    public Page<CommentDto> getList(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return commentService.findAllByStatus(Enums.CommentStatus.ACTIVE, pageable).map(CommentDto::new);
    }

    @GetMapping("{id}")
    public CommentDto getDetail(@PathVariable(name = "id") Long id) {
        Optional<Comments> optionalComments;
        optionalComments = commentService.findByIdAndStatus(id, Enums.CommentStatus.ACTIVE);
        if (!optionalComments.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        return new CommentDto(optionalComments.get());
    }

    @PostMapping("")
    public CommentDto create(@RequestBody CommentDto commentDto) {
        return new CommentDto(commentService.create(commentDto, commentDto.getUserId().getId()));
    }

    @PutMapping("")
    public String update(@RequestBody CommentDto request) {
        commentService.update(request, request.getUserId().getId());
        return messageResourceService.getMessage("update.success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        Optional<Comments> optionalComments;
        optionalComments = commentService.findByIdAndStatus(id, Enums.CommentStatus.ACTIVE);
        if (!optionalComments.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        commentService.deleteById(id, optionalComments.get().getUserId().getId());
        return new ResponseEntity<>(messageResourceService.getMessage("delete.success"), HttpStatus.OK);
    }

    @GetMapping("list/{id}")
    public Page<CommentDto> getList(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                    @PathVariable("id") long id) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return commentService.findAllByStatusAndBlogId(Enums.CommentStatus.ACTIVE, id, pageable).map(commentService::convertToCommentDto);
    }
}
