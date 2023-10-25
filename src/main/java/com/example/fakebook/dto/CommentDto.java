package com.example.fakebook.dto;

import com.example.fakebook.entities.Accounts;
import com.example.fakebook.entities.Blog;
import com.example.fakebook.entities.Comments;
import com.example.fakebook.utils.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private long id;
    private Accounts userId;
    private LocalDateTime createdAt;
    private long blogId;
    private Blog blog;
    private String content;
    private int likes;
    private long commentParent;
    private Enums.CommentStatus status = Enums.CommentStatus.ACTIVE;
    private List<Comments> commentsChild;

    public CommentDto(Comments comments) {
        BeanUtils.copyProperties(comments, this);
    }
}
