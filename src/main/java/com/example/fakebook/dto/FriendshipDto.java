package com.example.fakebook.dto;

import com.example.fakebook.entities.Accounts;
import com.example.fakebook.entities.Friendships;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipDto {
    private long id;
    private Accounts receiver;
    private Accounts sender;
    private String status;

    public FriendshipDto(Friendships friendships) {
        BeanUtils.copyProperties(friendships, this);
    }
}
