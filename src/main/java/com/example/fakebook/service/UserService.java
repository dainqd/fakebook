package com.example.fakebook.service;

import com.example.fakebook.dto.AccountDto;
import com.example.fakebook.dto.request.RegisterRequest;
import com.example.fakebook.dto.request.UpdateInfoRequest;
import com.example.fakebook.entities.Accounts;
import com.example.fakebook.entities.Roles;
import com.example.fakebook.repositories.AccountRepository;
import com.example.fakebook.repositories.RoleRepository;
import com.example.fakebook.utils.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.Cookie;

@Service
@RequiredArgsConstructor
public class UserService {
    final AccountRepository accountRepository;
    final RoleRepository roleRepository;
    final MessageResourceService messageResourceService;
    final RoleService roleService;
    final PasswordEncoder encoder;

    public static final String ACCESS_TOKEN_KEY = "accessToken";
    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Page<Accounts> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    public Optional<Accounts> findById(long id) {
        return accountRepository.findById(id);
    }

    public Accounts save(Accounts accounts) {
        return accountRepository.save(accounts);
    }

    public Accounts create(AccountDto accountDto, long adminId) {
        try {
            Accounts accounts = new Accounts();
            BeanUtils.copyProperties(accountDto, accounts);
            accounts.setCreatedAt(LocalDateTime.now());
            accounts.setCreatedBy(adminId);
            accounts.setPassword(encoder.encode(accountDto.getPassword()));
            accounts.setVerified(true);
            Set<Roles> roles = new HashSet<>();
            Roles userRole = roleService.findByName(Enums.Roles.USER)
                    .orElseThrow(() -> new RuntimeException(messageResourceService.getMessage("role.not.found")));
            roles.add(userRole);
            accounts.setRoles(roles);
            accounts.setStatus(Enums.AccountStatus.ACTIVE);

            return accountRepository.save(accounts);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("create.error"));

        }
    }

    public Accounts update(AccountDto accountDto, long adminID) {
        try {
            Optional<Accounts> optionalAccounts = accountRepository.findById(accountDto.getId());
            if (!optionalAccounts.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageResourceService.getMessage("account.not.found"));
            }
            Accounts accounts = optionalAccounts.get();

            BeanUtils.copyProperties(accountDto, accounts);
            accounts.setUpdatedAt(LocalDateTime.now());
            accounts.setUpdatedBy(adminID);
            return accountRepository.save(accounts);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("update.error"));
        }
    }

    public Accounts updateInfo(long accountID, UpdateInfoRequest updateInfoRequest) {
//        try {
        Optional<Accounts> optionalAccounts = accountRepository.findById(accountID);
        if (!optionalAccounts.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("account.not.found"));
        }
        Accounts accounts = optionalAccounts.get();

        BeanUtils.copyProperties(updateInfoRequest, accounts);
        return accountRepository.save(accounts);
//        } catch (Exception exception) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                    messageResourceService.getMessage("update.error"));
//        }
    }

    public Accounts changeEmail(long accountID, String email) {
//        try {
        Optional<Accounts> optionalAccounts = accountRepository.findById(accountID);
        if (!optionalAccounts.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("account.not.found"));
        }
        Accounts accounts = optionalAccounts.get();
        accounts.setEmail(email);
        return accountRepository.save(accounts);
//        } catch (Exception exception) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                    messageResourceService.getMessage("update.error"));
//        }
    }

    public Accounts changeUsername(long accountID, String username) {
//        try {
        Optional<Accounts> optionalAccounts = accountRepository.findById(accountID);
        if (!optionalAccounts.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("account.not.found"));
        }
        Accounts accounts = optionalAccounts.get();
        accounts.setUsername(username);
        return accountRepository.save(accounts);
//        } catch (Exception exception) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                    messageResourceService.getMessage("update.error"));
//        }
    }

    public Accounts changePassword(long accountID, RegisterRequest registerRequest) {
//        try {
        Optional<Accounts> optionalAccounts = accountRepository.findById(accountID);
        if (!optionalAccounts.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("account.not.found"));
        }
        Accounts accounts = optionalAccounts.get();

        boolean result = encoder.matches(registerRequest.getCurrentPassword(), accounts.getPassword());
        System.out.println(result);
        if (!result){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("account.password.incorrect"));
        }

        if (!registerRequest.getPassword().equals(registerRequest.getPasswordConfirm())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("account.password.invalid"));
        }
        accounts.setPassword(encoder.encode(registerRequest.getPassword()));
        return accountRepository.save(accounts);
//        } catch (Exception exception) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                    messageResourceService.getMessage("update.error"));
//        }
    }

    public void deleteById(long id, long adminID) {
        try {
            Optional<Accounts> accountsOptional = accountRepository.findById(id);
            if (!accountsOptional.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageResourceService.getMessage("account.not.found"));
            }
            accountsOptional.get().setStatus(Enums.AccountStatus.DELETED);
            accountsOptional.get().setDeletedAt(LocalDateTime.now());
            accountsOptional.get().setDeletedBy(adminID);
            accountRepository.save(accountsOptional.get());
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("cancel.error"));
        }
    }

    public Page<Accounts> findAllByStatus(Enums.AccountStatus status, Pageable pageable) {
        return accountRepository.findAllByStatus(status, pageable);
    }

    public Optional<Accounts> findByIdAndStatus(long id, Enums.AccountStatus status) {
        return accountRepository.findByIdAndStatus(id, status);
    }

    public Optional<Accounts> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public Optional<Accounts> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public boolean checkPasswordMatch(String rawPassword, Accounts accounts) {
        return passwordEncoder.matches(rawPassword, accounts.getPassword());
    }

    public void saveAccessCookie(HttpServletResponse response, String accessToken) {
        Cookie accessCookie = new Cookie(ACCESS_TOKEN_KEY, accessToken);
        accessCookie.setSecure(true);
        response.addCookie(accessCookie);
    }

    public Accounts active(Accounts account) {
        account.setVerified(true);
        account.setVerifyCode(null);
        account.setStatus(Enums.AccountStatus.ACTIVE);
        account.setUpdatedAt(LocalDateTime.now());
        account.setUpdatedBy(account.getId());
        return this.save(account);
    }

    public boolean checkVerifyCode(Accounts account, String verifyCode) {
        return account.getVerifyCode().equals(verifyCode);
    }
}
