package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.dto.ReadingDto;
import com.sep490.g49.shibadekiru.entity.Book;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Reading;

import java.util.List;

public interface IReadingService {
    public List<Reading> getReadingPartByLesson(Lesson lesson);

    public Reading getReadingById(Long id);

    public Reading createReading(Reading Reading);

    public Reading updateReading(Long id,Reading Reading);

    public void deleteReading(Long id);

    List<Reading> getAllReadingByLesson(Lesson lesson);
}
