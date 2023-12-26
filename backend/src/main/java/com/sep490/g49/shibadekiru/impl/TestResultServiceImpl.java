package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.*;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.*;
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

    @Autowired
    private ClassTestAssignRepository classTestAssignRepository;

    @Override
    public List<TestResult> getTestResultByTest(Test test) {
        List<TestResult> testResults = new ArrayList<>();
        List<TestSection> testSection = testSectionRepository.findTestSectionsByTestAndIsDeletedFalse(test);
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
        testResult.setNumberOfQuestion(testResultRequest.getNumberOfQuestion());
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
        List<TestSection> testSections = testSectionRepository.findTestSectionsByTestAndIsDeletedFalse(test);
        List<TestResult> testResults= new ArrayList<>();
        testSections.forEach(testSection -> {
            if (testResultRepository.findTestResultsByStudentAndTestSection(student,testSection) != null) {
                TestResult testResult = testResultRepository.findTestResultsByStudentAndTestSection(student, testSection);
                testResults.add(testResult);
            }
        });
        System.out.println(testResults.size());
        return testResults;
    }

    @Override
    public boolean checkTestResultExist(Student student, ClassTestAssign classTestAssign, TestSection testSection){
        if (testResultRepository.findTestResultsByStudentAndClassTestAssignAndTestSection(student,classTestAssign,testSection) != null)
            return true;
        else return false;
    }

    @Override
    public List<TestResult> getTestResultByTestAssignAndStudent(Long testId, Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        ClassTestAssign classTestAssign = classTestAssignRepository.findById(testId).orElseThrow();
        List<TestResult> testResults= new ArrayList<>();
        testResults = testResultRepository.findTestResultsByStudentAndClassTestAssign(student,classTestAssign);
        return testResults;
    }

    @Override
    public List<TestResult> getTestResultByTestAssign(ClassTestAssign classTestAssign) {
        List<TestResult> testResults = new ArrayList<>();
        testResults = testResultRepository.findTestResultsByClassTestAssign(classTestAssign);
        return testResults;
    }
}
