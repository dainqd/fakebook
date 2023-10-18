package com.example.fakebook.entities;

import com.example.fakebook.dto.MarketingDto;
import com.example.fakebook.entities.basic.BaseEntity;
import com.example.fakebook.utils.Enums;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "marketings")
public class Marketing extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Accounts user_id;
    private String thumbnail;
    @Lob
    private String content;
    @Enumerated(EnumType.STRING)
    private Enums.MarketingStatus status = Enums.MarketingStatus.INACTIVE;

    public Marketing(MarketingDto marketingDto) {
        BeanUtils.copyProperties(marketingDto, this);
    }
}
