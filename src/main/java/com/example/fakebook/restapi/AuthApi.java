package com.example.fakebook.restapi;

import com.example.fakebook.dto.reponse.JwtResponse;
import com.example.fakebook.dto.request.LoginRequest;
import com.example.fakebook.dto.request.RegisterRequest;
import com.example.fakebook.entities.Accounts;
import com.example.fakebook.entities.Roles;
import com.example.fakebook.utils.Enums;
import com.example.fakebook.service.MessageResourceService;
import com.example.fakebook.service.RoleService;
import com.example.fakebook.service.UserDetailsIpmpl;
import com.example.fakebook.service.UserDetailsServiceImpl;
import com.example.fakebook.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthApi {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    RoleService roleService;

    @Autowired
    MessageResourceService messageResourceService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
        Optional<Accounts> optionalUser = userDetailsService.findByUsername(loginRequest.getUsername());
        if (!optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.not.found"));
        }
        Accounts account = optionalUser.get();
        if (!account.isVerified()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.not.verified"));
        }
        if (account.getStatus() == Enums.AccountStatus.INACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.not.active"));
        }
        if (account.getStatus() == Enums.AccountStatus.BLOCKED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.banned"));
        }
        if (account.getStatus() == Enums.AccountStatus.DELETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.deleted"));
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername()
                        , loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsIpmpl userDetails = (UserDetailsIpmpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Validated @RequestBody RegisterRequest registerRequest) {
        Optional<Accounts> optionalUser = userDetailsService.findByUsername(registerRequest.getUsername());
        if (optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.username.exist"));
        }
        Optional<Accounts> userOptional = userDetailsService.findByEmail(registerRequest.getEmail());
        if (userOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.email.exist"));
        }
        if (registerRequest.getPassword().length() < 6 || Objects.equals(registerRequest.getPassword(), "")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.password.invalid"));
        }
        if (!Objects.equals(registerRequest.getPassword(), registerRequest.getPasswordConfirm())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageResourceService.getMessage("account.password.incorrect"));
        }
        registerRequest.setPassword(encoder.encode(registerRequest.getPassword()));
        Accounts accounts = new Accounts(registerRequest);
        accounts.setVerified(true);
        Set<Roles> roles = new HashSet<>();
        Roles userRole = roleService.findByName(Enums.Roles.USER)
                .orElseThrow(() -> new RuntimeException(messageResourceService.getMessage("role.not.found")));
        roles.add(userRole);
        accounts.setRoles(roles);
        accounts.setStatus(Enums.AccountStatus.ACTIVE);
        userDetailsService.save(accounts);
        return ResponseEntity.ok(messageResourceService.getMessage("register.success"));
    }
}
