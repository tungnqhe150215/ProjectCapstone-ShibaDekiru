package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Student;
import com.sep490.g49.shibadekiru.entity.Test;
import com.sep490.g49.shibadekiru.entity.TestResult;
import com.sep490.g49.shibadekiru.entity.TestSection;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.StudentRepository;
import com.sep490.g49.shibadekiru.repository.TestRepository;
import com.sep490.g49.shibadekiru.repository.TestResultRepository;
import com.sep490.g49.shibadekiru.repository.TestSectionRepository;
import com.sep490.g49.shibadekiru.service.ITestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestResultServiceImpl implements ITestResultService {
    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private TestSectionRepository testSectionRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private StudentRepository studentRepository;

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
        testResultRequest.setDoneTime(LocalDateTime.now());
        return testResultRepository.save(testResultRequest);
    }

    @Override
    public TestResult updateTestResult(TestResult testResultRequest) {
        TestResult testResult = testResultRepository.findTestResultsByStudentAndTestSection(testResultRequest.getStudent(),testResultRequest.getTestSection());
        testResult.setDoneTime(LocalDateTime.now());
        testResult.setResult(testResultRequest.getResult());
        return testResultRepository.save(testResult);
    }

    @Override
    public void deleteTestResult(Long id) {
        TestResult testResult = testResultRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        testResultRepository.delete(testResult);
    }

    @Override
    public List<TestResult> getTestResultByTestAndStudent(Long testId, Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        Test test = testRepository.findTestByTestId(testId);
        List<TestSection> testSections = testSectionRepository.findTestSectionsByTest(test);
        List<TestResult> testResults= new ArrayList<>();
        testSections.forEach(testSection -> {
            TestResult testResult = testResultRepository.findTestResultsByStudentAndTestSection(student,testSection);
            testResults.add(testResult);
        });
        return testResults;
    }

    @Override
    public boolean checkTestResultExist(Student student, TestSection testSection){
        if (testResultRepository.findTestResultsByStudentAndTestSection(student,testSection) != null)
            return true;
        else return false;
    }
}
