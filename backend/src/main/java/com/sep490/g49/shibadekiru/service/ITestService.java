package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.Test;

import java.util.Collection;
import java.util.List;

public interface ITestService {
    Test getTestById(Long testId);

    List<Test> getAllTestByLecture(Lectures lecture);

    Test createTest(Test test);

    Test updateTest(Long testId, Test testUpdate);

    void deleteTest(Long testId);
}
