package com.sep490.g49.shibadekiru.controller.lecture;

import com.sep490.g49.shibadekiru.dto.QuestionBankDto;
import com.sep490.g49.shibadekiru.entity.QuestionBank;
import com.sep490.g49.shibadekiru.service.IQuestionBankService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/admin/question")
public class LectureManageQuestionBankController {

    ModelMapper modelMapper;

    IQuestionBankService questionBankService;

    @GetMapping()
    public List<QuestionBankDto> getAllQuestion() {
        return questionBankService.getAllQuestion().stream().map(question -> modelMapper.map(question, QuestionBankDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionBankDto> getQuestionById(@PathVariable(name = "questionId") long questionId) {

        QuestionBank questionBank = questionBankService.getQuestionById(questionId);

        //convert Entity to DTO
        QuestionBankDto questionBankDto = modelMapper.map(questionBank, QuestionBankDto.class);

        return ResponseEntity.ok().body(questionBankDto);
    }

    @PostMapping()
    public ResponseEntity<QuestionBankDto> createQuestion(@RequestBody QuestionBankDto questionBankDto) {

        QuestionBank questionBankRequest = modelMapper.map(questionBankDto, QuestionBank.class);

        QuestionBank questionBank = questionBankService.createQuestion(questionBankRequest);

        QuestionBankDto questionBankResponse = modelMapper.map(questionBank, QuestionBankDto.class);

        return new ResponseEntity<QuestionBankDto>(questionBankResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionBankDto> updateQuestion(@PathVariable Long questionId, @RequestBody QuestionBankDto questionBankDto) {

        QuestionBank questionBankRequest = modelMapper.map(questionBankDto, QuestionBank.class);

        QuestionBank questionBank = questionBankService.updateQuestion(questionId, questionBankRequest);

        QuestionBankDto questionBankResponse = modelMapper.map(questionBank, QuestionBankDto.class);

        return ResponseEntity.ok().body(questionBankResponse);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Map<String, Boolean>> deleteQuestion(@PathVariable Long questionId) {
        questionBankService.deleteQuestion(questionId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}