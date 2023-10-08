package com.example.fakebook.entities;

import com.example.fakebook.dto.BlogDto;
import com.example.fakebook.dto.CommentDto;
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
@Table(name = "comments")
public class Comments extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Accounts userId;
    @OneToOne
    @JoinColumn(name = "blog_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Nullable
    private Blog blog;
    @Lob
    private String content;
    private int likes;
    @OneToOne
    @JoinColumn(name = "comment_parent")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Nullable
    private Comments parent;
    @Enumerated(EnumType.STRING)
    private Enums.CommentStatus status = Enums.CommentStatus.ACTIVE;

    public Comments(CommentDto commentDto) {
        BeanUtils.copyProperties(commentDto, this);
    }

    public Comments(Accounts userId, Blog blog, String content, int likes, Comments commentParent, Enums.CommentStatus status) {
        this.userId = userId;
        this.blog = blog;
        this.content = content;
        this.likes = likes;
        this.parent = commentParent;
        this.status = status;
    }
}
