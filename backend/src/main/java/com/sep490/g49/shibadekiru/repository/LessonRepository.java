package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Book;
import com.sep490.g49.shibadekiru.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByBook(Book book);

    @Query("SELECT l.book.bookId FROM Lesson l WHERE l.lessonId = :lessonId")
    Optional<Long> findBookIdByLessonId(@Param("lessonId") Long lessonId);

}
