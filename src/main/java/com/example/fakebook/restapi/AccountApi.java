package com.example.fakebook.restapi;

import com.example.fakebook.dto.AccountDto;
import com.example.fakebook.dto.request.RegisterRequest;
import com.example.fakebook.dto.request.UpdateInfoRequest;
import com.example.fakebook.entities.Accounts;
import com.example.fakebook.service.MessageResourceService;
import com.example.fakebook.service.UserDetailsServiceImpl;
import com.example.fakebook.service.UserService;
import com.example.fakebook.utils.Enums;
import com.example.fakebook.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AccountApi {
    final UserService userService;
    final MessageResourceService messageResourceService;

    @GetMapping("")
    public Page<AccountDto> getList(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return userService.findAllByStatus(Enums.AccountStatus.ACTIVE, pageable).map(AccountDto::new);
    }

    @GetMapping("{id}")
    public AccountDto getDetail(@PathVariable(name = "id") Long id) {
        Optional<Accounts> optionalAccount;
        optionalAccount = userService.findByIdAndStatus(id, Enums.AccountStatus.ACTIVE);
        if (!optionalAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("account.not.found"));
        }
        return new AccountDto(optionalAccount.get());
    }

    @PutMapping("/update-information/{id}")
    public String updateInfomation(@PathVariable(name = "id") Long id, @RequestBody UpdateInfoRequest request) {
        Optional<Accounts> optionalAccounts = userService.findById(id);
        if (!optionalAccounts.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        Accounts accounts = optionalAccounts.get();
        userService.updateInfo(accounts.getId(), request);
        return messageResourceService.getMessage("update.success");
    }

    @PutMapping("/change-email/{id}")
    public String changeEmail(@PathVariable(name = "id") Long id, @RequestBody String email) {
        Optional<Accounts> optionalAccounts = userService.findById(id);
        if (!optionalAccounts.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        Accounts accounts = optionalAccounts.get();
        userService.changeEmail(accounts.getId(), email);
        return messageResourceService.getMessage("update.success");
    }

    @PutMapping("/change-password/{id}")
    public String changePassword(@PathVariable(name = "id") Long id, @RequestBody RegisterRequest request) {
        Optional<Accounts> optionalAccounts = userService.findById(id);
        if (!optionalAccounts.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        Accounts accounts = optionalAccounts.get();
        userService.changePassword(accounts.getId(), request);
        return messageResourceService.getMessage("update.success");
    }

    @PutMapping("/change-username/{id}")
    public String changeUsername(@PathVariable(name = "id") Long id, @RequestBody String username) {
        Optional<Accounts> optionalAccounts = userService.findById(id);
        if (!optionalAccounts.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        Accounts accounts = optionalAccounts.get();
        userService.changeUsername(accounts.getId(), username);
        return messageResourceService.getMessage("update.success");
    }
}
