package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Listening;
import com.sep490.g49.shibadekiru.repository.ListeningRepository;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
import com.sep490.g49.shibadekiru.service.IListeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListeningServiceImpl implements IListeningService {

    @Autowired
    private ListeningRepository listeningRepository;

    @Autowired
    private GoogleDriveService googleDriveService;

    @Override
    public List<Listening> getListeningPartByLesson(Lesson lesson) {
        return listeningRepository.findListeningsByLesson(lesson).stream().peek(data ->
                {
                    if (data.getLink().length() > 0 && !data.getLink().equals("")){
                        data.setLink(googleDriveService.getFileUrl(data.getLink()));
                    }
                }

        ).collect(Collectors.toList());
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
        listening.setScript(listeningRequest.getScript());
        listening.setTitle(listeningRequest.getTitle());

        if (listeningRequest.getLink().length() > 0) {
            googleDriveService.deleteFile(listening.getLink());
            System.out.println("File đã xóa : " + listening.getLink());
            listening.setLink(listeningRequest.getLink());
        }
        else {
            listening.setLink(listening.getLink());
        }

        return listeningRepository.save(listening);
    }

    @Override
    public void deleteListening(Long id) {
        Listening listening = listeningRepository.findListeningByListeningId(id);

        if (listening.getLink() != null) {
            googleDriveService.deleteFile(listening.getLink());
            System.out.println("File đã xóa : " + listening.getLink());
        }

        listeningRepository.delete(listening);
    }
}
