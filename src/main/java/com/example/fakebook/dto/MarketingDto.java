package com.example.fakebook.dto;

import com.example.fakebook.entities.Accounts;
import com.example.fakebook.entities.Marketing;
import com.example.fakebook.entities.Message;
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
public class MarketingDto {
    private long id;
    private Accounts user_id;
    private String thumbnail;
    private String content;
    private Enums.MarketingStatus status = Enums.MarketingStatus.INACTIVE;

    public MarketingDto(Marketing marketing) {
        BeanUtils.copyProperties(marketing, this);
    }
}
