package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Token;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
      select t from Token t inner join UserAccount u\s
      on t.userAccount.userAccountId = u.userAccountId\s
      where u.userAccountId = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(Long id);

    @Query(value = """
      select t from Token t inner join UserAccount u\s
      on t.userAccount.userAccountId = u.userAccountId\s
      where u.userAccountId = :id and (t.expired = true or t.revoked = true)\s
      """)
    List<Token> findAllValidRefreshTokenByUser(Long id);

    @Query("SELECT u " +
            "FROM UserAccount u " +
            "JOIN u.tokens t " +
            "WHERE t.token = :token")
    UserAccount findUserAccountByToken(String token);

    Optional<Token> findByToken(String token);

    Optional<Token> findByUserAccountAndExpiredAndRevoked(UserAccount user, boolean expired, boolean revoked);
}
