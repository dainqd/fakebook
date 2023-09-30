package com.example.fakebook.dto;

import com.example.fakebook.entities.Accounts;
import com.example.fakebook.entities.Groups;
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
public class GroupDto {
    private long id;
    private Accounts admin;
    private String content;
    private Enums.GroupStatus status = Enums.GroupStatus.ACTIVE;

    public GroupDto(Groups groups) {
        BeanUtils.copyProperties(groups, this);
    }
}
