package com.example.fakebook.service;

import com.example.fakebook.dto.CommentDto;
import com.example.fakebook.entities.Comments;
import com.example.fakebook.repositories.CommentRepository;
import com.example.fakebook.utils.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    final CommentRepository commentRepository;
    final MessageResourceService messageResourceService;

    public Page<Comments> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    public Optional<Comments> findById(long id) {
        return commentRepository.findById(id);
    }

    public Comments save(Comments comments) {
        return commentRepository.save(comments);
    }

    public Comments create(CommentDto commentDto, long adminId) {
        try {
            Comments comments = new Comments();
            BeanUtils.copyProperties(commentDto, comments);
            comments.setCreatedAt(LocalDateTime.now());
            comments.setCreatedBy(adminId);
            comments.setStatus(Enums.CommentStatus.ACTIVE);

            return commentRepository.save(comments);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("create.error"));

        }
    }

    public Comments update(CommentDto commentDto, long adminID) {
        try {
            Optional<Comments> optionalComments = commentRepository.findById(commentDto.getId());
            if (!optionalComments.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageResourceService.getMessage("id.not.found"));
            }
            Comments comments = optionalComments.get();

            BeanUtils.copyProperties(commentDto, comments);
            comments.setUpdatedAt(LocalDateTime.now());
            comments.setUpdatedBy(adminID);
            return commentRepository.save(comments);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("update.error"));
        }
    }

    public void deleteById(long id, long adminID) {
        try {
            Optional<Comments> optionalComments = commentRepository.findById(id);
            if (!optionalComments.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageResourceService.getMessage("id.not.found"));
            }
            optionalComments.get().setStatus(Enums.CommentStatus.DELETED);
            optionalComments.get().setDeletedAt(LocalDateTime.now());
            optionalComments.get().setDeletedBy(adminID);
            commentRepository.save(optionalComments.get());
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("cancel.error"));
        }
    }

    public Page<Comments> findAllByStatus(Enums.CommentStatus status, Pageable pageable) {
        return commentRepository.findAllByStatus(status, pageable);
    }

    public Optional<Comments> findByIdAndStatus(long id, Enums.CommentStatus status) {
        return commentRepository.findByIdAndStatus(id, status);
    }
}