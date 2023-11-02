package com.sep490.g49.shibadekiru.controller.lecture;

import com.sep490.g49.shibadekiru.dto.TestDto;
import com.sep490.g49.shibadekiru.dto.TestResultDto;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.Test;
import com.sep490.g49.shibadekiru.entity.TestResult;
import com.sep490.g49.shibadekiru.service.ILecturesService;
import com.sep490.g49.shibadekiru.service.ITestResultService;
import com.sep490.g49.shibadekiru.service.ITestService;
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
@CrossOrigin(origins = "*")
@RequestMapping("/api/lecture/")
public class LectureManageTestController {

    @Autowired
    private ITestService iTestService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ILecturesService iLecturesService;

    @Autowired
    private ITestResultService iTestResultService;

    @GetMapping("/test")
    public List<TestDto> getAllTestByLecture() {

        Lectures lectures = iLecturesService.getLectureById(1L);

        return iTestService.getAllTestByLecture(lectures).stream().map(test -> modelMapper.map(test, TestDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/test/{testId}")
    public ResponseEntity<TestDto> getTestById(@PathVariable(name = "testId") long testId) {

        Test testBank = iTestService.getTestById(testId);

        //convert Entity to DTO
        TestDto testBankDto = modelMapper.map(testBank, TestDto.class);

        return ResponseEntity.ok().body(testBankDto);
    }

    @PostMapping("/test")
    public ResponseEntity<TestDto> createTest( @RequestBody TestDto testBankDto) {

        Test testRequest = modelMapper.map(testBankDto, Test.class);

        Test testBank = iTestService.createTest(testRequest);

        TestDto testBankResponse = modelMapper.map(testBank, TestDto.class);

        return new ResponseEntity<TestDto>(testBankResponse, HttpStatus.CREATED);
    }

    @PutMapping("/test/{testId}")
    public ResponseEntity<TestDto> updateQuestion(@PathVariable Long testId, @RequestBody TestDto testBankDto) {

        Test testRequest = modelMapper.map(testBankDto, Test.class);

        Test testBank = iTestService.updateTest(testId, testRequest);

        TestDto testBankResponse = modelMapper.map(testBank, TestDto.class);

        return ResponseEntity.ok().body(testBankResponse);
    }

    @DeleteMapping("/test/{testId}")
    public ResponseEntity<Map<String, Boolean>> deleteQuestion(@PathVariable Long testId) {
        iTestService.deleteTest(testId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test/{testId}/test-result")
    public List<TestResultDto> getAllTestResultByTest(@PathVariable(name = "testId") Long testId) {

        Test test = iTestService.getTestById(testId);

        return iTestResultService.getTestResultByTest(test).stream().map(testResult -> modelMapper.map(testResult, TestResultDto.class)).collect(Collectors.toList());
    }

    @PostMapping("/test/{testId}/test-result")
    public ResponseEntity<TestResultDto> createTestResult(@PathVariable(name = "testId") Long testId, @RequestBody TestResultDto testResultDto) {
        TestDto testDto = new TestDto();

        testDto.setTestId(testId);

        testResultDto.setTest(testDto);

        TestResult testResultRequest = modelMapper.map(testResultDto, TestResult.class);

        TestResult testResult = iTestResultService.createTestResult(testResultRequest);

        TestResultDto testBankResponse = modelMapper.map(testResult, TestResultDto.class);

        return new ResponseEntity<TestResultDto>(testBankResponse, HttpStatus.CREATED);
    }
}
