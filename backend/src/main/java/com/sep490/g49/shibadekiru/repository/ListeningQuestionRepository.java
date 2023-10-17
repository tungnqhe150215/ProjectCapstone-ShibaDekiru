package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.*;
import com.sep490.g49.shibadekiru.entity.Listening;
import com.sep490.g49.shibadekiru.entity.ListeningQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListeningQuestionRepository extends JpaRepository<ListeningQuestion,Long> {
    
    List<ListeningQuestion> findByListening(Listening listening);

    ListeningQuestion findListeningQuestionByListeningQuestionId(Long id);
}
