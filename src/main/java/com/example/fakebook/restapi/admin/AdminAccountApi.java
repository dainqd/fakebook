package com.example.fakebook.restapi.admin;

import com.example.fakebook.dto.AccountDto;
import com.example.fakebook.entities.Accounts;
import com.example.fakebook.repositories.RoleRepository;
import com.example.fakebook.service.MessageResourceService;
import com.example.fakebook.service.UserDetailsServiceImpl;
import com.example.fakebook.service.UserService;
import com.example.fakebook.utils.Enums;
import com.example.fakebook.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/api/user")
public class AdminAccountApi {
    final UserService userService;
    final RoleRepository roleRepository;
    final MessageResourceService messageResourceService;

    @GetMapping("")
    public Page<AccountDto> getList(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                    @RequestParam(value = "status", required = false, defaultValue = "") Enums.AccountStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (status != null) {
            return userService.findAllByStatus(status, pageable).map(AccountDto::new);
        }
        return userService.findAll(pageable).map(AccountDto::new);
    }

    @GetMapping("{id}")
    public AccountDto getDetail(@PathVariable(name = "id") Long id) {
        Optional<Accounts> optionalAccount;
        optionalAccount = userService.findById(id);
        if (!optionalAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("account.not.found"));
        }
        return new AccountDto(optionalAccount.get());
    }

    @PostMapping("")
    public AccountDto create(@RequestBody AccountDto accountDto) {
        String username = Utils.getUsername();
        Optional<Accounts> optionalAccounts = userService.findByUsername(username);
        if (!optionalAccounts.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        Accounts accounts = optionalAccounts.get();
        return new AccountDto(userService.create(accountDto, accounts.getId()));
    }

    @PutMapping("")
    public String update(@RequestBody AccountDto request) {
        String username = Utils.getUsername();
        Optional<Accounts> optionalAccounts = userService.findByUsername(username);
        if (!optionalAccounts.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        Accounts accounts = optionalAccounts.get();
        userService.update(request, accounts.getId());
        return messageResourceService.getMessage("update.success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        String username = Utils.getUsername();
        System.out.println(username);
        Optional<Accounts> optionalAccounts = userService.findByUsername(username);
        if (!optionalAccounts.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        Accounts accounts = optionalAccounts.get();
        userService.deleteById(id, accounts.getId());
        return new ResponseEntity<>(messageResourceService.getMessage("delete.success"), HttpStatus.OK);
    }
}
