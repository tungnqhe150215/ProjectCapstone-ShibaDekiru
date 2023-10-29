package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.dto.ReadingDto;
import com.sep490.g49.shibadekiru.entity.Book;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Reading;
import com.sep490.g49.shibadekiru.repository.ReadingRepository;
import com.sep490.g49.shibadekiru.service.IReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadingServiceImpl implements IReadingService {

    @Autowired
    private ReadingRepository readingRepository;

    @Override
    public List<Reading> getReadingPartByLesson(Lesson lesson) {
        return readingRepository.findReadingsByLesson(lesson);
    }

    @Override
    public Reading getReadingById(Long id) {
        return readingRepository.findReadingByReadingId(id);
    }

    @Override
    public Reading createReading(Reading reading) {
        return readingRepository.save(reading);
    }

    @Override
    public Reading updateReading(Long id, Reading readingRequest) {
        Reading reading = readingRepository.findReadingByReadingId(id);
        reading.setContent(readingRequest.getContent());
        reading.setImage(readingRequest.getImage());
        reading.setTitle(readingRequest.getTitle());
        return readingRepository.save(reading);
    }

    @Override
    public void deleteReading(Long id) {
        Reading reading = readingRepository.findReadingByReadingId(id);
        readingRepository.delete(reading);
    }

    @Override
    public List<Reading> getAllReadingByLesson(Lesson lesson) {
        return readingRepository.findReadingByLesson(lesson);
    }
}
