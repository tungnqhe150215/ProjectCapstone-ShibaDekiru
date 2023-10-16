package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Reading;
import com.sep490.g49.shibadekiru.entity.ReadingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadingQuestionRepository extends JpaRepository<ReadingQuestion,Long> {
    List<ReadingQuestion> findAllByReading(Reading reading);

    ReadingQuestion findReadingQuestionByReadingQuestionId(Long id);
}
