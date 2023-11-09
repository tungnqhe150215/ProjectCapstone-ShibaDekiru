package com.sep490.g49.shibadekiru.controller.lecture;

import com.sep490.g49.shibadekiru.dto.TestAssignDto;
import com.sep490.g49.shibadekiru.dto.TestDto;
import com.sep490.g49.shibadekiru.dto.TestResultDto;
import com.sep490.g49.shibadekiru.entity.*;
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

    @Autowired
    private IClassTestAssignService iClassTestAssignService;

    @Autowired
    private IClassService iClassService;

    @Autowired
    private ITestSectionService iTestSectionService;

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
    public ResponseEntity<TestDto> updateTest(@PathVariable Long testId, @RequestBody TestDto testBankDto) {

        Test testRequest = modelMapper.map(testBankDto, Test.class);

        Test testBank = iTestService.updateTest(testId, testRequest);

        TestDto testBankResponse = modelMapper.map(testBank, TestDto.class);

        return ResponseEntity.ok().body(testBankResponse);
    }

    @DeleteMapping("/test/{testId}")
    public ResponseEntity<Map<String, Boolean>> deleteTest(@PathVariable Long testId) {
        iTestService.deleteTest(testId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test/{id}/test-result")
    public List<TestResultDto> getAllTestResultByTest(@PathVariable(name = "id") Long testId) {
        Test test = iTestService.getTestById(testId);
        TestSection testSection = iTestSectionService.getTestSectionById(testId);

        return iTestResultService.getTestResultByTest(test).stream().map(testResult -> modelMapper.map(testResult, TestResultDto.class)).collect(Collectors.toList());
    }

    @PostMapping("/test/section/{sectionId}/test-result")
    public ResponseEntity<TestResultDto> createTestResult(@PathVariable(name = "sectionId") Long sectionId, @RequestBody TestResultDto testResultDto) {

        TestResult testResultRequest = modelMapper.map(testResultDto, TestResult.class);

        TestResult testResult = iTestResultService.createTestResult(testResultRequest);

        TestResultDto testBankResponse = modelMapper.map(testResult, TestResultDto.class);

        return new ResponseEntity<TestResultDto>(testBankResponse, HttpStatus.CREATED);
    }

    @GetMapping("/test/{testId}/assign")
    public List<TestAssignDto> getClassTestByTest( @PathVariable(name = "testId") Long testId) {

        Test test = iTestService.getTestById(testId);

        return iClassTestAssignService.getAllClassTestByTest(test).stream().map(classTestAssign -> modelMapper.map(classTestAssign, TestAssignDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/test/assign/{id}")
    public ResponseEntity<TestAssignDto> getTestAssignById(@PathVariable(name = "id") long id) {

        ClassTestAssign classTestAssign = iClassTestAssignService.getClassTestById(id);

        //convert Entity to DTO
        TestAssignDto testAssignDto = modelMapper.map(classTestAssign, TestAssignDto.class);

        return ResponseEntity.ok().body(testAssignDto);
    }

    @PostMapping("/test/assign")
    public ResponseEntity<TestAssignDto> createClassTest( @RequestBody TestAssignDto testAssignDto,@RequestParam("extendTime") int extendTime) {

        ClassTestAssign classTestAssignRequest = modelMapper.map(testAssignDto,ClassTestAssign.class);

        ClassTestAssign response =  iClassTestAssignService.saveClassTestAssign(classTestAssignRequest,extendTime);

        TestAssignDto assignDto = modelMapper.map(response,TestAssignDto.class);

        return new ResponseEntity<TestAssignDto>(assignDto, HttpStatus.CREATED);
    }

    @PutMapping("/test/assign")
    public ResponseEntity<TestAssignDto> updateClassTest( @RequestParam("id") Long id, @RequestParam("extendTime") int extendTime) {

        ClassTestAssign classTestAssign = iClassTestAssignService.updateExpireDate(id, extendTime);

        TestAssignDto assignDto = modelMapper.map(classTestAssign,TestAssignDto.class);

        return  ResponseEntity.ok().body(assignDto);
    }

    @DeleteMapping("/test/assign/{testId}")
    public ResponseEntity<Map<String, Boolean>> deleteClassTest(@PathVariable Long testId) {
        iClassTestAssignService.deleteClassTest(testId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
