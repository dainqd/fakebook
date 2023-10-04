package com.example.fakebook.entities;

import com.example.fakebook.dto.FriendshipDto;
import com.example.fakebook.entities.basic.BaseEntity;
import com.example.fakebook.utils.Enums;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "friendships")
public class Friendships extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "receiverId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Accounts receiverId;
    @OneToOne
    @JoinColumn(name = "senderId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Accounts senderId;
    @Enumerated(EnumType.STRING)
    private Enums.FriendshipStatus status = Enums.FriendshipStatus.NONE;

    public Friendships(FriendshipDto friendshipDto) {
        BeanUtils.copyProperties(friendshipDto, this);
    }
}
