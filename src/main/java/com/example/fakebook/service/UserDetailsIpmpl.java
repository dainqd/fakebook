package com.example.fakebook.service;

import com.example.fakebook.entities.Accounts;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class UserDetailsIpmpl implements UserDetails {
    public static final long serialVersionUID = 1L;

    private Long id;
    private String avt;
    private String firstname;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private String birthday;
    private String gender;
    private String address;
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsIpmpl(Long id, String avt, String firstname, String lastName, String username, String email,
                            String phoneNumber, String birthday, String gender, String address, String password,
                            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.avt = avt;
        this.firstname = firstname;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.password = password;
        this.authorities = authorities;
    }

    public static com.example.fakebook.service.UserDetailsIpmpl build(Accounts user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new com.example.fakebook.service.UserDetailsIpmpl(
                user.getId(),
                user.getAvt(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getBirthday(),
                user.getGender(),
                user.getAddress(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        com.example.fakebook.service.UserDetailsIpmpl user = (com.example.fakebook.service.UserDetailsIpmpl) o;
        return Objects.equals(id, user.getId());
    }
}
