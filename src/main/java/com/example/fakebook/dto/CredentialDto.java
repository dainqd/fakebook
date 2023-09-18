package com.example.fakebook.dto;

import com.example.fakebook.entities.Credential;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CredentialDto {
    private long id;
    private String accessToken;
    private String refreshToken;
    private long expiredAt;
    private String scope;
    private Long accountId;

    public CredentialDto(Credential credential) {
        BeanUtils.copyProperties(credential, this);
    }
}
