package com.example.fakebook.service;

import com.example.fakebook.entities.Accounts;
import com.example.fakebook.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    AccountRepository userRepository;

    public Optional<Accounts> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<Accounts> findByEmail(String emai) {
        return userRepository.findByEmail(emai);
    }

    public void save(Accounts user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Accounts user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return UserDetailsIpmpl.build(user);
    }
}
