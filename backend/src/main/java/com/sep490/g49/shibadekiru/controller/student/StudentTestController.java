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

import java.util.List;
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


    @GetMapping("/test/section/{id}/question")
    public List<QuestionBankDto> getQuestionBySection(@PathVariable("id") Long id) {
        TestSection testSection = iTestSectionService.getTestSectionById(id);
        return iQuestionBankService.getAllQuestionByTestSection(testSection).stream().map(questionBank -> map.map(questionBank, QuestionBankDto.class)).collect(Collectors.toList());
    }

    @PostMapping("/test/result")
    public ResponseEntity<TestResultDto> createTestResult(@RequestBody TestResultDto testResultDto) {

        TestResult testResultRequest = map.map(testResultDto, TestResult.class);

        TestResult testResult = iTestResultService.createTestResult(testResultRequest);

        TestResultDto testResultResponse = map.map(testResult, TestResultDto.class);

        return new ResponseEntity<TestResultDto>(testResultResponse, HttpStatus.CREATED);
    }
}
