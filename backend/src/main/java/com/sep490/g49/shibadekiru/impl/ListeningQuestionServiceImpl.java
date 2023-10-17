package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Listening;
import com.sep490.g49.shibadekiru.entity.ListeningQuestion;
import com.sep490.g49.shibadekiru.repository.ListeningQuestionRepository;
import com.sep490.g49.shibadekiru.service.IListeningQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListeningQuestionServiceImpl implements IListeningQuestionService {
    @Autowired
    private ListeningQuestionRepository listeningQuestionRepository;

    @Override
    public List<ListeningQuestion> getListeningQuestionByListening(Listening listening) {
        return listeningQuestionRepository.findByListening(listening);
    }

    @Override
    public ListeningQuestion getListeningQuestionById(Long id) {
        return listeningQuestionRepository.findListeningQuestionByListeningQuestionId(id);
    }

    @Override
    public ListeningQuestion createListeningQuestion(ListeningQuestion listeningQuestion) {
        return listeningQuestionRepository.save(listeningQuestion);
    }

    @Override
    public ListeningQuestion updateListeningQuestion(Long id, ListeningQuestion listeningQuestionRequest) {
        ListeningQuestion listeningQuestion = listeningQuestionRepository.findListeningQuestionByListeningQuestionId(id);
        listeningQuestion.setQuestion(listeningQuestionRequest.getQuestion());
        listeningQuestion.setFirstChoice(listeningQuestionRequest.getFirstChoice());
        listeningQuestion.setSecondChoice(listeningQuestionRequest.getSecondChoice());
        listeningQuestion.setThirdChoice(listeningQuestionRequest.getThirdChoice());
        listeningQuestion.setCorrectAnswer(listeningQuestionRequest.getCorrectAnswer());
        return listeningQuestionRepository.save(listeningQuestion);
    }

    @Override
    public void deleteListeningQuestion(Long id) {
        ListeningQuestion listeningQuestion = listeningQuestionRepository.findListeningQuestionByListeningQuestionId(id);
        listeningQuestionRepository.delete(listeningQuestion);
    }
}
