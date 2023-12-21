package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.dto.ReadingDto;
import com.sep490.g49.shibadekiru.entity.Book;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Reading;
import com.sep490.g49.shibadekiru.repository.ReadingRepository;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
import com.sep490.g49.shibadekiru.service.IReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReadingServiceImpl implements IReadingService {

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private GoogleDriveService googleDriveService;

    @Override
    public List<Reading> getReadingPartByLesson(Lesson lesson) {
        return readingRepository.findReadingsByLesson(lesson).stream().peek(data ->
                {
                    if (data.getImage().length() > 0 && !data.getImage().equals("")) {
                        data.setImage(googleDriveService.getFileUrl(data.getImage()));
                    }
                }
        ).collect(Collectors.toList());
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
        reading.setTitle(readingRequest.getTitle());

        if (readingRequest.getImage().length() > 0) {
            googleDriveService.deleteFile(reading.getImage());
            System.out.println("File đã xóa : " + reading.getImage());
            reading.setImage(readingRequest.getImage());
        } else {
            reading.setImage(reading.getImage());
        }


        return readingRepository.save(reading);
    }

    @Override
    public void deleteReading(Long id) {
        Reading reading = readingRepository.findReadingByReadingId(id);

        if (reading.getImage() != null) {
            googleDriveService.deleteFile(reading.getImage());
            System.out.println("File đã xóa : " + reading.getImage());
        }

        readingRepository.delete(reading);
    }

    @Override
    public List<Reading> getAllReadingByLesson(Lesson lesson) {
        return readingRepository.findReadingByLesson(lesson).stream().peek(data ->
                {
                    if (data.getImage().length() > 0 && !data.getImage().equals("")){
                        data.setImage(googleDriveService.getFileUrl(data.getImage()));
                    }
                }

        ).collect(Collectors.toList());
    }
}
