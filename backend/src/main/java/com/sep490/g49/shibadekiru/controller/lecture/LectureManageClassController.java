package com.sep490.g49.shibadekiru.controller.lecture;

import com.sep490.g49.shibadekiru.dto.ClassDto;
import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.service.IClassService;
import com.sep490.g49.shibadekiru.service.ILecturesService;
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
@RequestMapping("/api/lecture")
public class LectureManageClassController {

    ModelMapper modelMapper;

    IClassService classService;

    ILecturesService lectureService;

    @GetMapping("/{lectureId}/class")
    public List<ClassDto> getAllClassByLecture(@PathVariable (name = "lectureId") Long lectureId){
        Lectures lecturesResponse = lectureService.getLectureById(lectureId);

        return classService.getAllClassByLecture(lecturesResponse).stream().map(classLecture -> modelMapper.map(classLecture, ClassDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/class/{classId}")
    public ResponseEntity<ClassDto> getClassById(@PathVariable (name = "classId") Long classId) {

        Class classs = classService.getClassById(classId);

        ClassDto classResponse = modelMapper.map(classs, ClassDto.class);

        return ResponseEntity.ok().body(classResponse);
    }

    @PostMapping("/{lectureId}/class")
    public ResponseEntity<ClassDto> createClass(@PathVariable (name = "lectureId") Long lectureId, @RequestBody ClassDto classDto) {

        classDto.setLectureId(lectureId);

        Class classRequest = modelMapper.map(classDto, Class.class);

        Class classCreate = classService.createClass(classRequest);

        ClassDto classResonse = modelMapper.map(classCreate, ClassDto.class);

        return new ResponseEntity<ClassDto>(classResonse, HttpStatus.CREATED);
    }

    @PutMapping("/class/{classId}")
    public ResponseEntity<ClassDto> updateClass(@PathVariable (name = "classId") Long classId, @RequestBody ClassDto classDto) {

        Class classRequest = modelMapper.map(classDto, Class.class);

        Class classUpdate = classService.updateClass(classId, classRequest);

        ClassDto classResponse = modelMapper.map(classUpdate, ClassDto.class);

        return ResponseEntity.ok().body(classResponse);
    }

    @DeleteMapping("/class/{classId}")
    public ResponseEntity<Map<String, Boolean>> deleteClass(@PathVariable (name = "classId") Long classId) {
        classService.deleteClass(classId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
