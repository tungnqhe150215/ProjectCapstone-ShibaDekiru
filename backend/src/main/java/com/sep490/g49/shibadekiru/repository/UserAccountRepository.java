package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByEmail(String email);

    Optional<UserAccount> findByEmailOrMemberId(String email, String memberId);

    Optional<UserAccount> findByMemberId(String memberId);

    UserAccount findByUserName(String userName);

    UserAccount findByResetCode(String restCode);
}
