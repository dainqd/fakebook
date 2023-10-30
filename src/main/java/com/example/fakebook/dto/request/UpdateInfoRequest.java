package com.example.fakebook.dto.request;

import com.example.fakebook.entities.Accounts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInfoRequest {
    private String avt;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String birthday;
    private String gender;
    private String address;

    public UpdateInfoRequest(Accounts accounts){
        BeanUtils.copyProperties(accounts,this);
    }
}
