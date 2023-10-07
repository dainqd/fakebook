package com.example.fakebook.controller;

import com.example.fakebook.dto.request.LoginRequest;
import com.example.fakebook.dto.request.RegisterRequest;
import com.example.fakebook.entities.Accounts;
import com.example.fakebook.entities.Roles;
import com.example.fakebook.service.*;
import com.example.fakebook.utils.Enums;
import com.example.fakebook.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@RequestMapping("")
@Slf4j
public class AuthController {
    final UserService userDetailsService;
    final MessageResourceService messageResourceService;
    final HttpServletRequest request;
    final AuthenticationManager authenticationManager;
    final JwtUtils jwtUtils;
    final RoleService roleService;
    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("login")
    public String login(Model model) {
        LoginRequest loginRequest = new LoginRequest();
        model.addAttribute("loginRequest", loginRequest);
        return "v1/auth/login";
    }

    @GetMapping("register")
    public String register(Model model) {
        RegisterRequest signupRequest = new RegisterRequest();
        model.addAttribute("signupRequest", signupRequest);
        return "v1/auth/register";
    }

    @PostMapping("login")
    public String processServiceLogin(
            @Valid @ModelAttribute LoginRequest loginRequest,
            BindingResult result,
            Model model,
            HttpServletRequest request,
            HttpSession session,
            HttpServletResponse response) {
        Optional<Accounts> optionalUser = userDetailsService.findByUsername(loginRequest.getUsername());
        if (!optionalUser.isPresent()) {
            result.rejectValue("username", "400", messageResourceService.getMessage("account.not.found"));
            model.addAttribute("loginRequest", loginRequest);
            session.setAttribute("username", messageResourceService.getMessage("account.not.found"));
            return "v1/auth/login";
        }
        Accounts account = optionalUser.get();
        if (!account.isVerified()) {
            result.rejectValue("username", "400", messageResourceService.getMessage("account.not.verified"));
            model.addAttribute("loginRequest", loginRequest);
            return "v1/auth/login";
        }
        if (account.getStatus() == Enums.AccountStatus.INACTIVE) {
            result.rejectValue("username", "400", messageResourceService.getMessage("account.not.active"));
            model.addAttribute("loginRequest", loginRequest);
            return "v1/auth/login";
        }
        if (account.getStatus() == Enums.AccountStatus.BLOCKED) {
            result.rejectValue("username", "400", messageResourceService.getMessage("account.banned"));
            model.addAttribute("loginRequest", loginRequest);
            return "v1/auth/login";
        }
        if (account.getStatus() == Enums.AccountStatus.DELETED) {
            result.rejectValue("username", "400", messageResourceService.getMessage("account.deleted"));
            model.addAttribute("loginRequest", loginRequest);
            return "v1/auth/login";
        }
        // Xử lý check mật khẩu, add login history, update last login.
        boolean isMatch = userDetailsService.checkPasswordMatch(loginRequest.getPassword(), account);
        if (isMatch) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername()
                            , loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsIpmpl userDetails = (UserDetailsIpmpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            userDetailsService.saveAccessCookie(response, jwt);
            Cookie username = new Cookie("username", loginRequest.getUsername());
            response.addCookie(username);
            return "redirect:/";
        } else {
            result.rejectValue("password", "400", messageResourceService.getMessage("account.password.incorrect"));
            model.addAttribute("loginRequest", loginRequest);
            return "v1/auth/login";
        }
    }

    @PostMapping("register")
    public String register(
            @Valid @ModelAttribute RegisterRequest signupRequest,
            BindingResult result,
            Model model, HttpSession session) {
        Optional<Accounts> optionalUser = userDetailsService.findByUsername(signupRequest.getUsername());
        if (optionalUser.isPresent()) {
            result.rejectValue("username", "400", messageResourceService.getMessage("account.username.exist"));
            model.addAttribute("signupRequest", signupRequest);
            return "v1/auth/register";
        }
        Optional<Accounts> userOptional = userDetailsService.findByEmail(signupRequest.getEmail());
        if (userOptional.isPresent()) {
            result.rejectValue("email", "400", messageResourceService.getMessage("account.email.exist"));
            model.addAttribute("signupRequest", signupRequest);
            return "v1/auth/register";
        }
        if (!Objects.equals(signupRequest.getPassword(), signupRequest.getPasswordConfirm())) {
            result.rejectValue("password", "400", messageResourceService.getMessage("account.password.incorrect"));
            model.addAttribute("signupRequest", signupRequest);
            return "v1/auth/register";
        }
        if (signupRequest.getPassword().length() < 5 || signupRequest.getPassword() == null || Objects.equals(signupRequest.getPassword(), "")) {
            result.rejectValue("password", "400", messageResourceService.getMessage("account.password.invalid"));
            model.addAttribute("signupRequest", signupRequest);
            return "v1/auth/register";
        }
        signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        Accounts accounts = new Accounts(signupRequest);
        accounts.setVerified(true);
        Set<Roles> roles = new HashSet<>();
        Roles userRole = roleService.findByName(Enums.Roles.USER)
                .orElseThrow(() -> new RuntimeException(messageResourceService.getMessage("role.not.found")));
        roles.add(userRole);
        accounts.setRoles(roles);
        accounts.setStatus(Enums.AccountStatus.ACTIVE);
        System.out.println(accounts);
        userDetailsService.save(accounts);
        model.addAttribute("signupRequest", signupRequest);
        return "redirect:/login";
    }
}
