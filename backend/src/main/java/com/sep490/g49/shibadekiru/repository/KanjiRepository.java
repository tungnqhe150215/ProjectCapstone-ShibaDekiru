package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Kanji;
import com.sep490.g49.shibadekiru.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KanjiRepository extends JpaRepository<Kanji, Long> {

    List<Kanji> findKanjiByLesson(Lesson lesson);
}
