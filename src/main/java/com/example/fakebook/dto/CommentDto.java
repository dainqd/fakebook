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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private long id;
    private Accounts user_id;
    private Blog blog_id;
    private String content;
    private int likes;
    private Comments comment_parent;
    private Enums.CommentStatus status = Enums.CommentStatus.ACTIVE;

    public CommentDto(Comments comments) {
        BeanUtils.copyProperties(comments, this);
    }
}
