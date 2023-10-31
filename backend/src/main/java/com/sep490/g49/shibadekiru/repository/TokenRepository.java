package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
      select t from Token t inner join UserAccount u\s
      on t.userAccount.userAccountId = u.userAccountId\s
      where u.userAccountId = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(Long id);

    Optional<Token> findByToken(String token);
}
