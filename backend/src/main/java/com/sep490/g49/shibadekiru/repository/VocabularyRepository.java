package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {
}
