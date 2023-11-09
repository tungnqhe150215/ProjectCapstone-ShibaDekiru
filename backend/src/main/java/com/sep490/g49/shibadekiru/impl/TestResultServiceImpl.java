package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Test;
import com.sep490.g49.shibadekiru.entity.TestResult;
import com.sep490.g49.shibadekiru.entity.TestSection;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.TestResultRepository;
import com.sep490.g49.shibadekiru.repository.TestSectionRepository;
import com.sep490.g49.shibadekiru.service.ITestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestResultServiceImpl implements ITestResultService {
    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private TestSectionRepository testSectionRepository;

    @Override
    public List<TestResult> getTestResultByTest(Test test) {
        List<TestResult> testResults = new ArrayList<>();
        List<TestSection> testSection = testSectionRepository.findTestSectionsByTest(test);
        for (TestSection section: testSection){
            List<TestResult> results = testResultRepository.findTestResultsByTestSection(section);
            for (TestResult result : results){
                if (!testResults.contains(result)){
                    testResults.add(result);
                }
            }
        }
        return testResults;
    }

    @Override
    public TestResult getTestResultById(Long id) {
        TestResult testResult = testResultRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        return testResult;
    }

    @Override
    public TestResult createTestResult(TestResult testResultRequest) {
        testResultRequest.setDoneTime(LocalDateTime.parse(LocalDateTime.now().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return testResultRepository.save(testResultRequest);
    }

    @Override
    public TestResult updateTestResult(Long id, TestResult testResultRequest) {
        TestResult testResult = testResultRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        return testResultRepository.save(testResult);
    }

    @Override
    public void deleteTestResult(Long id) {
        TestResult testResult = testResultRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        testResultRepository.delete(testResult);
    }
}
