package com.example.fakebook.entities;

import com.example.fakebook.dto.GroupDto;
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
@ToString
@Table(name = "groups_members")
public class GroupMembers extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "admin_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Accounts users;
    @OneToOne
    @JoinColumn(name = "group_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Groups groups;
    @Enumerated(EnumType.STRING)
    private Enums.GroupMembersStatus status = Enums.GroupMembersStatus.ACTIVE;

    public GroupMembers(GroupDto groupDto) {
        BeanUtils.copyProperties(groupDto, this);
    }
}
