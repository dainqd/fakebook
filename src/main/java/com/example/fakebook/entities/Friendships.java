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
    @JoinColumn(name = "user_id_first")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Accounts user_id_first;
    @OneToOne
    @JoinColumn(name = "user_id_second")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Accounts user_id_second;
    @Enumerated(EnumType.STRING)
    private Enums.FriendshipStatus status = Enums.FriendshipStatus.ACTIVE;

    public Friendships(FriendshipDto friendshipDto) {
        BeanUtils.copyProperties(friendshipDto, this);
    }
}
