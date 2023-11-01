package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.Test;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.LecturersRepository;
import com.sep490.g49.shibadekiru.repository.TestRepository;
import com.sep490.g49.shibadekiru.service.ITestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestServiceImpl implements ITestService {

    LecturersRepository lecturersRepository;

    TestRepository testRepository;

    @Override
    public List<Test> getAllTestByLecture(Lectures lecture) {
        return testRepository.findAllByLecture(lecture);
    }

    @Override
    public Test getTestById(Long testId) {

        Test test = testRepository.findById(testId).orElse(null);

        if(test == null) {
            throw new ResourceNotFoundException("Test not found with id: " + testId);
        }
        return test;
    }


}
