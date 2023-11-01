package com.sep490.g49.shibadekiru.controller.lecture;

import com.sep490.g49.shibadekiru.dto.ClassDto;
import com.sep490.g49.shibadekiru.dto.LecturesDto;
import com.sep490.g49.shibadekiru.dto.TestDto;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.Test;
import com.sep490.g49.shibadekiru.service.ILecturesService;
import com.sep490.g49.shibadekiru.service.ITestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/lecture")
public class LectureManageTestController {

    ModelMapper modelMapper;

    ITestService testService;

    ILecturesService lecturesService;

    @GetMapping("/{lectureId}/test")
    public List<TestDto> getAllTestByLecture (@PathVariable (name = "lectureId") Long lectureId) {
        Lectures lecturesResponse = lecturesService.getLectureById(lectureId);
        return testService.getAllTestByLecture(lecturesResponse).stream().map(testLecture -> modelMapper.map(testLecture, TestDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/test/{testId}")
    public ResponseEntity<TestDto> getTestById (@PathVariable (name = "testId") Long testId) {
        Test test = testService.getTestById(testId);

        TestDto testResponse = modelMapper.map(test, TestDto.class);

        return ResponseEntity.ok().body(testResponse);
    }

    @PostMapping("/{lectureId}/test")
    public ResponseEntity<TestDto> createTest (@PathVariable (name = "lectureId") Long lectureId, @RequestBody TestDto testDto) {

        LecturesDto lecturesDto = new LecturesDto();
        lecturesDto.setLectureId(lectureId);
        testDto.setLecture(lecturesDto);

        Test testRequest = modelMapper.map(testDto, Test.class);

        Test testCreate = testService.createTest(testRequest);

        TestDto testResponse = modelMapper.map(testCreate, TestDto.class);

        return new ResponseEntity<TestDto>(testResponse, HttpStatus.CREATED);
    }

}
