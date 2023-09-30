package com.example.fakebook.dto;

import com.example.fakebook.entities.Accounts;
import com.example.fakebook.entities.GroupMembers;
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
public class GroupMemberDto {
    private long id;
    private Accounts users;
    private Groups groups;
    private Enums.GroupMembersStatus status = Enums.GroupMembersStatus.ACTIVE;

    public GroupMemberDto(GroupMembers groupMembers) {
        BeanUtils.copyProperties(groupMembers, this);
    }
}
