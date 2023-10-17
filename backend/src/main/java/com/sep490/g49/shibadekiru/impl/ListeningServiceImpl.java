package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Listening;
import com.sep490.g49.shibadekiru.repository.ListeningRepository;
import com.sep490.g49.shibadekiru.service.IListeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListeningServiceImpl implements IListeningService {

    @Autowired
    private ListeningRepository listeningRepository;

    @Override
    public List<Listening> getListeningPartByLesson(Lesson lesson) {
        return listeningRepository.findListeningsByLesson(lesson);
    }

    @Override
    public Listening getListeningById(Long id) {
        return listeningRepository.findListeningByListeningId(id);
    }

    @Override
    public Listening createListening(Listening listening) {
        return listeningRepository.save(listening);
    }

    @Override
    public Listening updateListening(Long id, Listening listeningRequest) {
        Listening listening = listeningRepository.findListeningByListeningId(id);
        listening.setLink(listeningRequest.getLink());
        listening.setScript(listeningRequest.getScript());
        listening.setTitle(listeningRequest.getTitle());
        return listeningRepository.save(listening);
    }

    @Override
    public void deleteListening(Long id) {
        Listening listening = listeningRepository.findListeningByListeningId(id);
        listeningRepository.delete(listening);
    }
}
