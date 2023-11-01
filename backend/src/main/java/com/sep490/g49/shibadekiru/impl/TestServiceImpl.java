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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestServiceImpl implements ITestService {

    LecturersRepository lecturersRepository;

    TestRepository testRepository;
    
    @Override
    public Test getTestById(Long testId) {

        Test test = testRepository.findById(testId).orElse(null);

        if(test == null) {
            throw new ResourceNotFoundException("Test not found with id: " + testId);
        }
        return test;
    }

    @Override
    public Test createTest(Test test) {
        return testRepository.save(test);
    }

    @Override
    public Test updateTest(Long id, Test testRequest) {
        Test test = testRepository.findTestByTestId(id);
        test.setDuration(testRequest.getDuration());
        test.setTitle(testRequest.getTitle());
        test.setCreatedAt(LocalDateTime.parse(LocalDateTime.now().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        test.setIsLocked(testRequest.getIsLocked());
        return testRepository.save(test);
    }

    @Override
    public void deleteTest(Long id) {
        Test test = testRepository.findTestByTestId(id);
        testRepository.delete(test);
    }

    @Override
    public List<Test> getAllTestByLecture(Lectures lectures) {
        return testRepository.findTestsByLecture(lectures);
    }
}
