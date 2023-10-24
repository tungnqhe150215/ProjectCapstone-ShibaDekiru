package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.dto.ClassWorkDto;
import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.ClassWork;

import java.util.List;

public interface IClassWorkService {


    public List<ClassWork> getClassWorkByClass(Class aclass);

    public ClassWorkDto createClassWork(ClassWorkDto classWorkDto);

    public ClassWork updateClassWork(Long classWorkId , ClassWork classWork);

    public void deleteClassWork(Long id);

    public void updateIsLocked(Long classWorkId);

    public ClassWork getClassWorkById(Long classWorkId);
}
