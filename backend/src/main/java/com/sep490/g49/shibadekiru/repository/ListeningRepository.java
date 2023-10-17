package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Listening;
import com.sep490.g49.shibadekiru.entity.Listening;
import com.sep490.g49.shibadekiru.entity.Listening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListeningRepository extends JpaRepository<Listening, Long> {
    List<Listening> findListeningsByLesson(Lesson lesson);

    Listening findListeningByListeningId(Long id);
}
