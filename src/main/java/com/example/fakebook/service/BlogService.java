package com.example.fakebook.service;

import com.example.fakebook.dto.BlogDto;
import com.example.fakebook.entities.Blog;
import com.example.fakebook.repositories.BlogRepository;
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
public class BlogService {
    final BlogRepository blogRepository;
    final MessageResourceService messageResourceService;

    public Page<Blog> findAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    public Optional<Blog> findById(long id) {
        return blogRepository.findById(id);
    }

    public Blog save(Blog blog) {
        return blogRepository.save(blog);
    }

    public Blog create(BlogDto blogDto, long adminId) {
        try {
            Blog blog = new Blog();
            BeanUtils.copyProperties(blogDto, blog);
            blog.setCreatedAt(LocalDateTime.now());
            blog.setCreatedBy(adminId);
            blog.setStatus(Enums.BlogStatus.ACTIVE);

            return blogRepository.save(blog);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("create.error"));

        }
    }

    public Blog update(BlogDto blogDto, long adminID) {
        try {
            Optional<Blog> blogOptional = blogRepository.findById(blogDto.getId());
            if (!blogOptional.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageResourceService.getMessage("id.not.found"));
            }
            Blog blog = blogOptional.get();

            BeanUtils.copyProperties(blogDto, blog);
            blog.setUpdatedAt(LocalDateTime.now());
            blog.setUpdatedBy(adminID);
            return blogRepository.save(blog);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("update.error"));
        }
    }

    public void deleteById(long id, long adminID) {
        try {
            Optional<Blog> blogOptional = blogRepository.findById(id);
            if (!blogOptional.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageResourceService.getMessage("id.not.found"));
            }
            blogOptional.get().setStatus(Enums.BlogStatus.DELETED);
            blogOptional.get().setDeletedAt(LocalDateTime.now());
            blogOptional.get().setDeletedBy(adminID);
            blogRepository.save(blogOptional.get());
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("cancel.error"));
        }
    }

    public Page<Blog> findAllByStatus(Enums.BlogStatus status, Pageable pageable) {
        return blogRepository.findAllByStatus(status, pageable);
    }

    public Optional<Blog> findByIdAndStatus(long id, Enums.BlogStatus status) {
        return blogRepository.findByIdAndStatus(id, status);
    }

    public Blog likeBlog(long id, long check) {
        Optional<Blog> blogOptional = blogRepository.findById(id);
        if (!blogOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("id.not.found"));
        }
        Blog blog = blogOptional.get();
        if (check == 1) {
            blog.setLikes(blog.getLikes() + 1);
        } else {
            blog.setLikes(blog.getLikes() - 1);
        }
        blogRepository.save(blog);
        return blog;
    }
}
