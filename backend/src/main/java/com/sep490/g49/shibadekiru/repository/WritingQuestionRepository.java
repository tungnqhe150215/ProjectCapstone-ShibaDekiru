package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Writing;
import com.sep490.g49.shibadekiru.entity.WritingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WritingQuestionRepository extends JpaRepository<WritingQuestion, Long> {
    List<WritingQuestion> findAllByWriting(Writing writing);
}
