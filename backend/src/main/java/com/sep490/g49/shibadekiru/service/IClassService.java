package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.Lectures;

import java.util.Collection;
import java.util.List;

public interface IClassService {

    Class getClassById(Long classId);

    List<Class> getAllClassByLecture(Lectures lecture);

    Class createClass(Class classs);

    Class updateClass(Long classId, Class classUpdate);

    void deleteClass(Long classId);

    List<Class> getAllClass();

    void updateIsLocked(Long id);
}
