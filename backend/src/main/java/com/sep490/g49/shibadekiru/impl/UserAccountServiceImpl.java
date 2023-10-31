package com.sep490.g49.shibadekiru.impl;

import ch.qos.logback.classic.Logger;
import com.sep490.g49.shibadekiru.entity.Role;
import com.sep490.g49.shibadekiru.entity.Token;
import com.sep490.g49.shibadekiru.entity.TokenType;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.RoleRepository;
import com.sep490.g49.shibadekiru.repository.TokenRepository;
import com.sep490.g49.shibadekiru.repository.UserAccountRepository;
import com.sep490.g49.shibadekiru.service.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAccountServiceImpl implements IUserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTServiceImpl jwtService;

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public List<UserAccount> getAllUserAccounts() {
        return userAccountRepository.findAll();
    }

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        Long roleId = 3L;
        String password = passwordEncoder.encode(userAccount.getPassword());

        Optional<Role> roleOptional = roleRepository.findById(roleId);
        if (roleOptional.isPresent()) {

            Role role = roleOptional.get();

            UserAccount userAccount1 = new UserAccount();

            userAccount1.setNickName(userAccount.getNickName());
            userAccount1.setMemberId(userAccount.getMemberId());
            userAccount1.setUserName(userAccount.getUsername());
            userAccount1.setPassword(password);
            userAccount1.setEmail(userAccount.getEmail());
            userAccount1.setResetCode(userAccount.getResetCode());
            userAccount1.setIsBanned(userAccount.getIsBanned());
            userAccount1.setRole(role);

            return userAccountRepository.save(userAccount1);


        }
        else {
            throw new ResourceNotFoundException("User Account can't be added.");
        }
    }

    @Override
    public UserAccount updateUserAccount(Long userAccountId, UserAccount userAccount) {
        Optional<UserAccount> existingUserAccount = userAccountRepository.findById(userAccountId);

        if (existingUserAccount.isPresent()) {

            UserAccount userAccount1 = existingUserAccount.get();

            userAccount1.setNickName(userAccount.getNickName());
            userAccount1.setMemberId(userAccount.getMemberId());
            userAccount1.setUserName(userAccount.getUsername());
            userAccount1.setPassword(userAccount.getPassword());
            userAccount1.setEmail(userAccount.getEmail());
            userAccount1.setResetCode(userAccount.getResetCode());
            userAccount1.setIsBanned(userAccount.getIsBanned());
            userAccount1.setRole(userAccount.getRole());

            return userAccountRepository.save(userAccount1);
        } else {
            throw new ResourceNotFoundException("Lesson not found with id: " + userAccountId);
        }

    }

    @Override
    public void updateIsBanned(Long userAccountId) {
        Optional<UserAccount> existingUserAccount = userAccountRepository.findById(userAccountId);

        if (existingUserAccount.isPresent()) {

            UserAccount userAccount = existingUserAccount.get();
            Boolean currentIsBanned = userAccount.getIsBanned();

            userAccount.setIsBanned(!currentIsBanned);

             userAccountRepository.save(userAccount);
        } else {
            throw new ResourceNotFoundException("Lesson not found with id: " + userAccountId);
        }
    }


    @Override
    public UserAccount getUserAccountById(Long userAccountId) {
        UserAccount userAccount = userAccountRepository.findById(userAccountId).orElse(null);

        if (userAccount == null) {
            throw new ResourceNotFoundException("Lesson not found with id: " +  userAccountId);
        }
        return userAccount;
    }

}
