package com.sep490.g49.shibadekiru.controller.student;

import com.sep490.g49.shibadekiru.dto.*;
import com.sep490.g49.shibadekiru.entity.*;
import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/student")
public class StudentTestController {

    @Autowired
    private ITestService iTestService;

    @Autowired
    private ITestSectionService iTestSectionService;

    @Autowired
    private IQuestionBankService iQuestionBankService;

    @Autowired
    private IClassTestAssignService iClassTestAssignService;

    @Autowired
    private ITestResultService iTestResultService;

    @Autowired
    private IClassService iClassService;

    @Autowired
    private ModelMapper map;

    @GetMapping("/class/{id}/test")
    public List<TestAssignDto> getAllTestByClass(@PathVariable("id") Long id) {
        Class aClass = iClassService.getClassById(id);

        return iClassTestAssignService.getAllClassTestByClass(aClass).stream().map(test -> map.map(test, TestAssignDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/test/assign")
    public TestAssignDto getTestByClassAndTest(@RequestParam("classId") Long id,@RequestParam("testId") Long testId) {
        ClassTestAssign classTestAssign = iClassTestAssignService.getClassTestByClassAndTest(id,testId);
        if (classTestAssign == null ){
            return null;
        }
        TestAssignDto testAssignDto = map.map(classTestAssign,TestAssignDto.class);
        return testAssignDto;
    }

    @GetMapping("/test/{id}")
    public TestDto getTestById(@PathVariable("id") Long id) {

        Test test = iTestService.getTestById(id);

        TestDto testDto = map.map(test,TestDto.class);

        return testDto;
    }

    @GetMapping("/test/{id}/section")
    public List<TestSectionDto> getTestSectionByTest(@PathVariable("id") Long id) {
        Test test = iTestService.getTestById(id);
        return iTestSectionService.getTestSectionByTest(test).stream().map(testSection -> map.map(testSection, TestSectionDto.class)).collect(Collectors.toList());
    }


    @GetMapping("/test/{id}/question")
    public List<QuestionBankDto> getQuestionByTest(@PathVariable("id") Long id) {
        Test test = iTestService.getTestById(id);

        return iQuestionBankService.getQuestionByTest(test).stream().map(questionBank -> map.map(questionBank, QuestionBankDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/test/section/{id}/question")
    public List<QuestionBankDto> getQuestionBySection(@PathVariable("id") Long id) {
        TestSection testSection = iTestSectionService.getTestSectionById(id);
        return iQuestionBankService.getAllQuestionByTestSection(testSection).stream().map(questionBank -> map.map(questionBank, QuestionBankDto.class)).collect(Collectors.toList());
    }

    @PostMapping("/test/result")
    public ResponseEntity<TestResultDto> createTestResult(@RequestBody TestResultDto testResultDto) {

        TestResult testResultRequest = map.map(testResultDto, TestResult.class);

        TestResult testResult = new TestResult();

        if (iTestResultService.checkTestResultExist(testResultRequest.getStudent(),testResultRequest.getClassTestAssign(),testResultRequest.getTestSection())){
           testResult = iTestResultService.updateTestResult(testResultRequest);
        } else {
            testResult = iTestResultService.createTestResult(testResultRequest);
        }
        TestResultDto testResultResponse = map.map(testResult, TestResultDto.class);

        return new ResponseEntity<TestResultDto>(testResultResponse, HttpStatus.CREATED);
    }

    @GetMapping("/test/result")
    public List<TestResultDto> getTestResultByTestAndStudent(@RequestParam("testId") Long testId, @RequestParam("studentId") Long studentId) {
        return iTestResultService.getTestResultByTestAndStudent(testId,studentId)
                        .stream().map(testResult -> map.map(testResult, TestResultDto.class)).collect(Collectors.toList());
    }

    @GetMapping("test/assign/result")
    public List<TestResultDto> getTestResultByTestAssignAndStudent(@RequestParam("testAssignId") Long testId, @RequestParam("studentId") Long studentId) {
        return iTestResultService.getTestResultByTestAssignAndStudent(testId,studentId)
                .stream().map(testResult -> map.map(testResult, TestResultDto.class)).collect(Collectors.toList());
    }

    @DeleteMapping("/test/result")
    public ResponseEntity<Map<String, Boolean>> deleteTestResultByTestAssignAndStudent(@RequestParam("testAssignId") Long testId, @RequestParam("studentId") Long studentId) {
        List<TestResult> list = iTestResultService.getTestResultByTestAssignAndStudent(testId, studentId);
        list.forEach(testResult -> {
            iTestResultService.deleteTestResult(testResult.getTestResultId());
        });
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
