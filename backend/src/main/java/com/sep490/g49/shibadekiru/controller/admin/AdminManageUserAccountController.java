package com.sep490.g49.shibadekiru.controller.admin;


import com.sep490.g49.shibadekiru.dto.UserAccountDto;
import com.sep490.g49.shibadekiru.dto.UserAccountRegisterDto;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import com.sep490.g49.shibadekiru.service.IUserAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/admin")
public class AdminManageUserAccountController {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IUserAccountService iUserAccountService;

    @GetMapping("/user-account")
    public List<UserAccountDto> getAllUserAccounts() {
        return iUserAccountService.getAllUserAccounts().stream().map(userAccount -> modelMapper.map(userAccount, UserAccountDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/user-account/{id}")
    public ResponseEntity<UserAccountDto> getUserAccountById(@PathVariable Long id) {
        UserAccount userAccount = iUserAccountService.getUserAccountById(id);

        UserAccountDto userAccountDto = modelMapper.map(userAccount, UserAccountDto.class);

        return ResponseEntity.ok().body(userAccountDto);

    }

    @PostMapping("/user-account")
    public ResponseEntity<?> createUserAccount(@RequestBody UserAccountRegisterDto userAccountDto) {
        try {
            iUserAccountService.createUserAccount(userAccountDto);
            return ResponseEntity.ok().build();
        }catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/user-account/{id}")
    public ResponseEntity<UserAccountDto> updateUserAccount(@PathVariable Long id, @RequestBody UserAccountDto userAccountDto) {
        UserAccount userAccountRequest = modelMapper.map(userAccountDto, UserAccount.class);

        UserAccount userAccount = iUserAccountService.updateUserAccountByIsCreatedByAdmin(id, userAccountRequest);

        UserAccountDto userAccountResponse = modelMapper.map(userAccount, UserAccountDto.class);

       return ResponseEntity.ok().body(userAccountResponse);
    }

    @PutMapping("/user-account/update-is-banned/{id}")
    public ResponseEntity<Void> updateIsBanned(@PathVariable Long id) {
        iUserAccountService.updateIsBanned(id);
        return ResponseEntity.ok().build();
    }



}
