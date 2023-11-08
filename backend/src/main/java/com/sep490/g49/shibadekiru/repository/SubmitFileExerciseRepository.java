package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Exercise;
import com.sep490.g49.shibadekiru.entity.SubmitFileExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmitFileExerciseRepository extends JpaRepository<SubmitFileExercise, Long> {

    List<SubmitFileExercise> findSubmitFileExercisesByExercise(Exercise exercise);

    SubmitFileExercise findSubmitFileExerciseById(Long id);
}
