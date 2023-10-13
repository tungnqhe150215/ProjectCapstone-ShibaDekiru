package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Grammar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrammarRepository extends JpaRepository<Grammar, Long> {
}
