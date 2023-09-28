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
}
