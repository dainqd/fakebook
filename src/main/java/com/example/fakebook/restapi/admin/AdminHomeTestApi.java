package com.example.fakebook.restapi.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api")
public class AdminHomeTestApi {
    @GetMapping("/hello")
    public ResponseEntity<?> getHello() {
        return ResponseEntity.ok("Hello admin");
    }
}
