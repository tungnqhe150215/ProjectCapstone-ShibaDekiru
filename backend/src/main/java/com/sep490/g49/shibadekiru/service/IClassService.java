package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Class;

import java.util.List;

public interface IClassService {

    Class getClassById(Long classId);

    List<Class> getAllClass();

    Class createClass(Class classRequest);

    Class updateClass(Long id, Class classRequest);

    void deleteClass(Long id);
}
