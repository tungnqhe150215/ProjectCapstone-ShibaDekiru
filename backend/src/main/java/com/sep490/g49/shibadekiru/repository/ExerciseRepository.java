package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.ClassWork;
import com.sep490.g49.shibadekiru.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Exercise findByExerciseId (Long id);

    List<Exercise> findExercisesByClassWork(ClassWork classWork);
}
