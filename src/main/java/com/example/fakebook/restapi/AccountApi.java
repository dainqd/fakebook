package com.example.fakebook.restapi;

import com.example.fakebook.dto.AccountDto;
import com.example.fakebook.dto.request.RegisterRequest;
import com.example.fakebook.dto.request.UpdateInfoRequest;
import com.example.fakebook.entities.Accounts;
import com.example.fakebook.service.MessageResourceService;
import com.example.fakebook.service.UserService;
import com.example.fakebook.utils.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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

    @GetMapping("find/user-by-email/{email}")
    public AccountDto findUserByEmail(@PathVariable(name = "email") String email) {
        Optional<Accounts> optionalAccount;
        optionalAccount = userService.findByEmail(email);
        if (!optionalAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("account.not.found"));
        }
        return new AccountDto(optionalAccount.get());
    }

    @GetMapping("find/user-by-username/{username}")
    public AccountDto findUserByUsername(@PathVariable(name = "username") String username) {
        Optional<Accounts> optionalAccount;
        optionalAccount = userService.findByUsername(username);
        if (!optionalAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("account.not.found"));
        }
        return new AccountDto(optionalAccount.get());
    }

    @PutMapping("/update-information/{id}")
    public String updateInfomation(@PathVariable(name = "id") Long id, @RequestBody UpdateInfoRequest request) {
        userService.updateInfo(id, request);
        return messageResourceService.getMessage("update.success");
    }

    @PutMapping("/change-email/{id}")
    public String changeEmail(@PathVariable(name = "id") Long id, @RequestBody String email) {
        userService.changeEmail(id, email);
        return messageResourceService.getMessage("update.success");
    }

    @PutMapping("/change-password/{id}")
    public String changePassword(@PathVariable(name = "id") Long id, @RequestBody RegisterRequest request) {
        userService.changePassword(id, request);
        return messageResourceService.getMessage("update.success");
    }

    @PutMapping("/change-username/{id}")
    public String changeUsername(@PathVariable(name = "id") Long id, @RequestBody String username) {
        userService.changeUsername(id, username);
        return messageResourceService.getMessage("update.success");
    }

    @PutMapping("/change-avt/{id}")
    public String changeAvt(@PathVariable(name = "id") Long id, @RequestBody String avt) {
        userService.changeAvt(id, avt);
        return messageResourceService.getMessage("update.success");
    }

    @PutMapping("/change-thumbnail/{id}")
    public String changeThumbnail(@PathVariable(name = "id") Long id, @RequestBody String thumbnail) {
        userService.changeThumbnail(id, thumbnail);
        return messageResourceService.getMessage("update.success");
    }

    @GetMapping("/getUser/{id}")
    public List<AccountDto> getAllUserNoFriends(@PathVariable(name = "id") Long id) {
        return userService.getAllAccountByReceiverId(id);
    }
}
