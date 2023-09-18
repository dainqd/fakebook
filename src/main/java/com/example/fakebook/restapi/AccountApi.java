package com.example.fakebook.restapi;

import com.example.fakebook.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class AccountApi {
    @Autowired
    UserDetailsServiceImpl userDetailsServiceimpl;

    @GetMapping("/hello")
    public ResponseEntity<?> getList(){
        return ResponseEntity.ok("Hello User");
    }
}
