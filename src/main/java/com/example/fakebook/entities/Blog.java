package com.example.fakebook.entities;

import com.example.fakebook.dto.AccountDto;
import com.example.fakebook.dto.BlogDto;
import com.example.fakebook.entities.basic.BaseEntity;
import com.example.fakebook.utils.Enums;
import lombok.*;
import org.springframework.beans.BeanUtils;
import reactor.util.annotation.Nullable;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "blogs")
public class Blog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Accounts user_id;
    private String thumbnail;
    @Lob
    private String content;
    private int likes;
    private int views;
    private int comments;
    private int shares;
    @Enumerated(EnumType.STRING)
    private Enums.BlogStatus status = Enums.BlogStatus.ACTIVE;

    public Blog(BlogDto blogDto) {
        BeanUtils.copyProperties(blogDto, this);
    }

    public Blog(Accounts user_id, String thumbnail, String content, int likes, int views, int comments, int shares, Enums.BlogStatus status) {
        this.user_id = user_id;
        this.thumbnail = thumbnail;
        this.content = content;
        this.likes = likes;
        this.views = views;
        this.comments = comments;
        this.shares = shares;
        this.status = status;
    }
}
