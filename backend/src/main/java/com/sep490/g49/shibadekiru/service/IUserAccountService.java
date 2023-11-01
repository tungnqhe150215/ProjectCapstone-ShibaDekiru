package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.UserAccount;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface IUserAccountService {

    List<UserAccount> getAllUserAccounts();

    UserAccount createUserAccount(UserAccount userAccount);

    UserAccount updateUserAccount(Long userAccountId, UserAccount userAccount);

    @Modifying
    void updateIsBanned(Long userAccountId);

    UserAccount getUserAccountById(Long userAccountId);


}
