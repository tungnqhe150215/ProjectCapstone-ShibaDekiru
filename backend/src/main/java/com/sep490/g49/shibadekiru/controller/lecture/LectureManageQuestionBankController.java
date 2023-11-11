package com.sep490.g49.shibadekiru.controller.lecture;

import com.sep490.g49.shibadekiru.dto.QuestionBankDto;
import com.sep490.g49.shibadekiru.dto.TestSectionDto;
import com.sep490.g49.shibadekiru.entity.QuestionBank;
import com.sep490.g49.shibadekiru.entity.SectionType;
import com.sep490.g49.shibadekiru.entity.Test;
import com.sep490.g49.shibadekiru.entity.TestSection;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
import com.sep490.g49.shibadekiru.service.IQuestionBankService;
import com.sep490.g49.shibadekiru.service.ITestSectionService;
import com.sep490.g49.shibadekiru.service.ITestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/lecture/test")
public class LectureManageQuestionBankController {

    ModelMapper modelMapper;

    IQuestionBankService questionBankService;

    ITestService testService;

    ITestSectionService iTestSectionService;

    GoogleDriveService googleDriveService;

    @GetMapping("/section/{sectionId}/question")
    public List<QuestionBankDto> getAllQuestionByTestSection(@PathVariable (name = "sectionId") Long sectionId) {

        TestSection testSection = iTestSectionService.getTestSectionById(sectionId);

        return questionBankService.getAllQuestionByTestSection(testSection).stream().map(question -> modelMapper.map(question, QuestionBankDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/section/question/{questionId}")
    public ResponseEntity<QuestionBankDto> getQuestionById(@PathVariable(name = "questionId") long questionId) {

        QuestionBank questionBank = questionBankService.getQuestionById(questionId);

        //convert Entity to DTO
        QuestionBankDto questionBankDto = modelMapper.map(questionBank, QuestionBankDto.class);

        return ResponseEntity.ok().body(questionBankDto);
    }

    @PostMapping("/section/{sectionId}/question")
    public ResponseEntity<QuestionBankDto> createQuestion(@PathVariable (name = "sectionId") Long sectionId,@RequestBody QuestionBankDto questionBankDto) {

        QuestionBank questionBankRequest = modelMapper.map(questionBankDto, QuestionBank.class);

        questionBankRequest.setSection(iTestSectionService.getTestSectionById(sectionId));

        QuestionBank questionBank = questionBankService.createQuestion(questionBankRequest);

        QuestionBankDto questionBankResponse = modelMapper.map(questionBank, QuestionBankDto.class);

        return new ResponseEntity<QuestionBankDto>(questionBankResponse, HttpStatus.CREATED);
    }

    @PutMapping("/section/question/{questionId}")
    public ResponseEntity<QuestionBankDto> updateQuestion(@PathVariable Long questionId, @RequestBody QuestionBankDto questionBankDto) {

        QuestionBank questionBankRequest = modelMapper.map(questionBankDto, QuestionBank.class);

        QuestionBank questionBank = questionBankService.updateQuestion(questionId, questionBankRequest);

        QuestionBankDto questionBankResponse = modelMapper.map(questionBank, QuestionBankDto.class);

        return ResponseEntity.ok().body(questionBankResponse);
    }

    @DeleteMapping("/section/question/{questionId}")
    public ResponseEntity<Map<String, Boolean>> deleteQuestion(@PathVariable Long questionId) {
        questionBankService.deleteQuestion(questionId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{testId}/section")
    public List<TestSectionDto> getAllSectionByTestAndType(@PathVariable (name = "testId") Long testId, @RequestParam("type") String sectionType) {

        Test test = testService.getTestById(testId);

        SectionType type = SectionType.valueOf(sectionType);

        List<TestSectionDto> testSectionDtos = iTestSectionService.getTestSectionByTypeAndTest(type,test).stream().map(section -> modelMapper.map(section, TestSectionDto.class)).collect(Collectors.toList());
        if(sectionType.equalsIgnoreCase("LISTENING")){
            for (TestSectionDto testSectionDto: testSectionDtos){
                testSectionDto.setSectionAttach(googleDriveService.getFileUrl(testSectionDto.getSectionAttach()));
            }
        }
        return testSectionDtos;
    }

    @GetMapping("/section/{questionId}")
    public ResponseEntity<TestSectionDto> getSectionById(@PathVariable(name = "questionId") long questionId) {

        TestSection testSection = iTestSectionService.getTestSectionById(questionId);

        //convert Entity to DTO
        TestSectionDto testSectionDto = modelMapper.map(testSection, TestSectionDto.class);

        return ResponseEntity.ok().body(testSectionDto);
    }

    @PostMapping("/{testId}/section")
    public ResponseEntity<TestSectionDto> createTestSection(@PathVariable (name = "testId") Long testId,@RequestBody TestSectionDto testSectionDto) {

        TestSection testSectionRequest = modelMapper.map(testSectionDto, TestSection.class);

        testSectionRequest.setSectionType(SectionType.valueOf(testSectionDto.getSectionType()));

        testSectionRequest.setTest(testService.getTestById(testId));

        TestSection testSection = iTestSectionService.createTestSection(testSectionRequest);

        TestSectionDto testSectionResponse = modelMapper.map(testSection, TestSectionDto.class);

        return new ResponseEntity<TestSectionDto>(testSectionResponse, HttpStatus.CREATED);
    }

    @PutMapping("/section/{sectionId}")
    public ResponseEntity<TestSectionDto> updateSection(@PathVariable Long sectionId, @RequestBody TestSectionDto testSectionDto) {

        TestSection testSectionRequest = modelMapper.map(testSectionDto, TestSection.class);

        TestSection testSection = iTestSectionService.updateTestSection(sectionId, testSectionRequest);

        TestSectionDto testSectionResponse = modelMapper.map(testSection, TestSectionDto.class);

        return ResponseEntity.ok().body(testSectionResponse);
    }

    @DeleteMapping("/section/{sectionId}")
    public ResponseEntity<Map<String, Boolean>> deleteSection(@PathVariable Long sectionId) {
        iTestSectionService.deleteTestSection(sectionId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
