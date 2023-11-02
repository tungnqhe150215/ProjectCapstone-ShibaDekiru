package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.dto.UserAccountDto;
import com.sep490.g49.shibadekiru.entity.UserAccount;

import java.util.List;

public interface IUserAccountService {

    List<UserAccount> getAllUserAccounts();

    UserAccount createUserAccount(UserAccount userAccount);

    UserAccount updateUserAccount(Long userAccountId, UserAccount userAccount);

    void updateIsBanned(Long userAccountId);

    UserAccount getUserAccountById(Long userAccountId);

}
