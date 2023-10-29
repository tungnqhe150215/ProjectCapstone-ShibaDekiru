package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Writing;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.WritingRepository;
import com.sep490.g49.shibadekiru.service.IWritingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WritingServiceImpl implements IWritingService {

    @Autowired
    private WritingRepository writingRepository;

    @Override
    public List<Writing> getWritingPartByLesson(Lesson lesson) {
        return writingRepository.findByLesson(lesson);
    }

    @Override
    public Writing getWritingById(Long id) {
        Writing writing = writingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        return writing;
    }

    @Override
    public Writing createWriting(Writing writingRequest) {
        return writingRepository.save(writingRequest);
    }

    @Override
    public Writing updateWriting(Long id, Writing writingRequest) {
        Writing writing = writingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        writing.setTopic(writingRequest.getTopic());
        return writingRepository.save(writing);
    }

    @Override
    public void deleteWriting(Long id) {
        Writing writing = writingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        writingRepository.delete(writing);
    }

}
