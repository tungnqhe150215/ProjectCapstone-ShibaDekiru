package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Writing;

import java.util.Collection;
import java.util.List;

public interface IWritingService {
    public List<Writing> getWritingPartByLesson(Lesson lesson);

    public Writing getWritingById(Long id);

    public Writing createWriting(Writing writing);

    public Writing updateWriting(Long id,Writing writing);

    public void deleteWriting(Long id);

}
