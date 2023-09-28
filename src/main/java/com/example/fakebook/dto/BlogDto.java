package com.example.fakebook.dto;

import com.example.fakebook.entities.Accounts;
import com.example.fakebook.entities.Blog;
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
public class BlogDto {
    private long id;
    private Accounts user_id;
    private String content;
    private int likes;
    private int views;
    private int comments;
    private int shares;
    private Enums.BlogStatus status = Enums.BlogStatus.ACTIVE;

    public BlogDto(Blog blog) {
        BeanUtils.copyProperties(blog, this);
    }
}
