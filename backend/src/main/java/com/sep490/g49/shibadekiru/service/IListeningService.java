package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Listening;

import java.util.List;

public interface IListeningService {
    List<Listening> getListeningPartByLesson(Lesson lesson);

    Listening getListeningById(Long id);

    Listening createListening(Listening listening);

    Listening updateListening(Long id, Listening listeningRequest);

    void deleteListening(Long id);
}
