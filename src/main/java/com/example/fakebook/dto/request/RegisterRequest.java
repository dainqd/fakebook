package com.example.fakebook.dto.request;

import com.example.fakebook.entities.Accounts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String passwordConfirm;

    public RegisterRequest(Accounts registerRequest){
        BeanUtils.copyProperties(registerRequest,this);
    }
}
