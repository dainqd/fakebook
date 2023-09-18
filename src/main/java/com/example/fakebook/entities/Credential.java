package com.example.fakebook.entities;

import com.example.fakebook.entities.basic.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "credential")
public class Credential extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String accessToken;
    private String refreshToken;
    private long expiredAt;
    private String scope;
    private Long accountId;
}
