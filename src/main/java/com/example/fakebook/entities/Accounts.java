package com.example.fakebook.entities;

import com.example.fakebook.dto.AccountDto;
import com.example.fakebook.dto.request.RegisterRequest;
import com.example.fakebook.dto.request.UpdateInfoRequest;
import com.example.fakebook.entities.basic.BaseEntity;
import com.example.fakebook.utils.Enums;
import lombok.*;
import org.springframework.beans.BeanUtils;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "accounts")
public class Accounts extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Lob
    private String avt;
    @Lob
    private String thumbnail;
    private String firstName;
    private String lastName;
    @NotNull(message = "Username cannot be left blank")
    private String username;
    @NotNull(message = "Incorrect email format!, Please re-enter")
    private String email;
    private String phoneNumber;
    private String birthday;
    private String gender;
    private String address;
    private String verifyCode = "";
    private String referralCode = "";
    private boolean verified = false;
    private int likes = 0;
    private int views = 0;
    @NotNull(message = "Password cannot be left blank")
    @Size(min = 6, message = "password must be greater than or equal to 6")
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "accounts_roles", joinColumns = @JoinColumn(name = "account_id")
            , inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private Enums.AccountStatus status = Enums.AccountStatus.INACTIVE;

    public Accounts(String avt, String username, String email) {
        this.avt = avt;
        this.username = username;
        this.email = email;
    }

    public Accounts(AccountDto accountDto) {
        BeanUtils.copyProperties(accountDto, this);
    }

    public Accounts(RegisterRequest registerRequest) {
        BeanUtils.copyProperties(registerRequest, this);
    }

    public Accounts(UpdateInfoRequest updateInfoRequest) {
        BeanUtils.copyProperties(updateInfoRequest, this);
    }

    public Accounts(String avt, String username, String email, String password, Enums.AccountStatus accountStatus) {
        this.avt = avt;
        this.thumbnail = "https://i.stack.imgur.com/l60Hf.png";
        this.username = username;
        this.email = email;
        this.password = password;
        this.status = accountStatus;
    }
}
