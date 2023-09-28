package com.example.fakebook.entities;

import com.example.fakebook.entities.basic.BaseEntity;
import com.example.fakebook.utils.Enums;
import lombok.*;
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
    private Accounts user_id;
    @Lob
    private String content;
    private int likes;
    @OneToOne
    @JoinColumn(name = "comment_parent")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Nullable
    private Comments comment_parent;
    @Enumerated(EnumType.STRING)
    private Enums.CommentStatus status = Enums.CommentStatus.ACTIVE;
}
