package com.example.fakebook.entities;

import com.example.fakebook.dto.GroupDto;
import com.example.fakebook.dto.GroupMemberDto;
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
@Table(name = "groups")
public class Groups extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "admin_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Accounts admin;
    private String content;
    @Enumerated(EnumType.STRING)
    private Enums.GroupStatus status = Enums.GroupStatus.ACTIVE;

    public Groups(GroupMemberDto groupMemberDto) {
        BeanUtils.copyProperties(groupMemberDto, this);
    }
}
