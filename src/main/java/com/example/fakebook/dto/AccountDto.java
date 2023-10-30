package com.example.fakebook.dto;

import com.example.fakebook.entities.Accounts;
import com.example.fakebook.entities.Roles;
import com.example.fakebook.utils.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private long id;
    private String avt;
    private String thumbnail;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private String birthday;
    private String gender;
    private String address;
    private String verifyCode = "";
    private String referralCode = "";
    private boolean verified = false;
    private int likes;
    private int views;
    private String password;
    private String token = "";
    private Enums.AccountState state = Enums.AccountState.OFFLINE;
    private Set<Roles> roles = new HashSet<>();
    private Enums.AccountStatus status = Enums.AccountStatus.INACTIVE;
    private Enums.TypeUser type = Enums.TypeUser.NORMAL;

    public AccountDto(Accounts accounts) {
        BeanUtils.copyProperties(accounts, this);
    }
}
