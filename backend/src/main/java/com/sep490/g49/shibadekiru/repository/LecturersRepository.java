package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturersRepository extends JpaRepository<Lectures, Long> {

    Lectures findByUserAccount(UserAccount userAccount);
}
