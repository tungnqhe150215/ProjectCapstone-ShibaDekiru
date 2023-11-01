package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.Test;

import java.util.List;

public interface ITestService {
    Test getTestById(Long testId);

    Test createTest(Test test);

    Test updateTest(Long id, Test testRequest);

    void deleteTest(Long id);

    List<Test> getAllTestByLecture(Lectures lectures);
}
