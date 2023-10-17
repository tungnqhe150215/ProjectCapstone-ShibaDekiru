package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Listening;
import com.sep490.g49.shibadekiru.entity.ListeningQuestion;

import java.util.List;

public interface IListeningQuestionService {
    List<ListeningQuestion> getListeningQuestionByListening(Listening listening);

    ListeningQuestion getListeningQuestionById(Long id);

    ListeningQuestion createListeningQuestion(ListeningQuestion listeningQuestion);

    ListeningQuestion updateListeningQuestion(Long id, ListeningQuestion listeningQuestionRequest);

    void deleteListeningQuestion(Long id);
}
