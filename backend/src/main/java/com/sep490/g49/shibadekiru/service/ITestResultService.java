package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.*;

import java.util.List;

public interface ITestResultService {

    List<TestResult> getTestResultByTest(Test test);

    TestResult getTestResultById(Long id);

    TestResult createTestResult(TestResult testResultRequest);

    TestResult updateTestResult(TestResult testResultRequest);

    void deleteTestResult(Long id);

    List<TestResult> getTestResultByTestAndStudent(Long testId, Long studentId);

    boolean checkTestResultExist(Student student, TestSection testSection);

    List<TestResult> getTestResultByTestAssignAndStudent(Long testId, Long studentId);

    List<TestResult> getTestResultByTestAssign(ClassTestAssign classTestAssign);
}
