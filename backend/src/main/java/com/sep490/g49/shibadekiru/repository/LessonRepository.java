package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
