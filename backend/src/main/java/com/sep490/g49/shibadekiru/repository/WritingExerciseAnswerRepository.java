package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Student;
import com.sep490.g49.shibadekiru.entity.WritingExercise;
import com.sep490.g49.shibadekiru.entity.WritingExerciseAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WritingExerciseAnswerRepository extends JpaRepository<WritingExerciseAnswer, Long> {

    WritingExerciseAnswer findByStudentAndAndWritingExercise(Student student, WritingExercise writingExercise);
}
