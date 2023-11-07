package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Test;
import com.sep490.g49.shibadekiru.entity.TestResult;
import com.sep490.g49.shibadekiru.entity.TestSection;

import java.util.List;

public interface ITestResultService {
    List<TestResult> getTestResultByTest(TestSection test);

    TestResult getTestResultById(Long id);

    TestResult createTestResult(TestResult testResultRequest);

    TestResult updateTestResult(Long id, TestResult testResultRequest);

    void deleteTestResult(Long id);
}
