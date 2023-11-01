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

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Test createTest(Test test) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String title = test.getTitle();
        Long duration = test.getDuration();
        Boolean isLocked = test.getIsLocked();
        Lectures lecture = test.getLecture();

        Test testCreate = new Test();
        testCreate.setTitle(title);
        testCreate.setCreatedAt(currentDateTime);
        testCreate.setDuration(duration);
        testCreate.setIsLocked(isLocked);
        testCreate.setLecture(lecture);
        return testRepository.save(testCreate);
    }

    @Override
    public Test updateTest(Long testId, Test testUpdate) {

        Optional<Test> testOptional = testRepository.findById(testId);

        if (testOptional.isPresent()) {
            Test test = testOptional.get();
            test.setTitle(testUpdate.getTitle());
            test.setDuration(testUpdate.getDuration());
            test.setIsLocked(testUpdate.getIsLocked());
            //ko cần set lại id của Lecture
            return testRepository.save(test);
        } else {
            throw new ResourceNotFoundException("Test not found");
        }
    }

}
