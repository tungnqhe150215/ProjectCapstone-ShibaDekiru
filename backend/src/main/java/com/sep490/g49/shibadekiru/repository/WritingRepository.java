package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Writing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WritingRepository extends JpaRepository<Writing, Long> {

    List<Writing> findByLesson(Lesson lesson);

    Writing findByWritingId(Long id);

}
