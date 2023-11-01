package com.sep490.g49.shibadekiru.controller.lecture;

import com.sep490.g49.shibadekiru.dto.ClassDto;
import com.sep490.g49.shibadekiru.dto.TestDto;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.service.ILecturesService;
import com.sep490.g49.shibadekiru.service.ITestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
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
    public List<TestDto> getAllTestByLecture (@PathVariable(name = "lectureId") Long lectureId) {
        Lectures lecturesResponse = lecturesService.getLectureById(lectureId);
        return testService.getAllTestByLecture(lecturesResponse).stream().map(testLecture -> modelMapper.map(testLecture, TestDto.class)).collect(Collectors.toList());
    }

}
