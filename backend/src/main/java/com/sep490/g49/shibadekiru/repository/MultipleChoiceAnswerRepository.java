package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Exercise;
import com.sep490.g49.shibadekiru.entity.MultipleChoiceAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MultipleChoiceAnswerRepository extends JpaRepository<MultipleChoiceAnswer, Long> {

    List<MultipleChoiceAnswer> findMultipleChoiceAnswersByExercise(Exercise exercise);

    MultipleChoiceAnswer findMultipleChoiceAnswerByMultipleChoiceAnswerId(Long id);
}
