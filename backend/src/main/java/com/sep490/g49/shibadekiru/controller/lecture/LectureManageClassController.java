package com.sep490.g49.shibadekiru.controller.lecture;

import com.sep490.g49.shibadekiru.dto.ClassDto;
import com.sep490.g49.shibadekiru.dto.QuestionBankDto;
import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.service.IClassService;
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
@RequestMapping("/api/lecture/class")
public class LectureManageClassController {

    ModelMapper modelMapper;

    IClassService classService;

    @GetMapping()
    public List<ClassDto> getAllClass(){
        return classService.getAllClass().stream().map(classLecture -> modelMapper.map(classLecture, ClassDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/{classId}")
    public ResponseEntity<ClassDto> getClassById(@PathVariable (name = "classId") Long classId) {

        Class classs = classService.getClassById(classId);

        ClassDto classResponse = modelMapper.map(classs, ClassDto.class);

        return ResponseEntity.ok().body(classResponse);
    }

    @PostMapping()
    public ResponseEntity<ClassDto> createClass(@RequestBody ClassDto classDto) {

        Class classRequest = modelMapper.map(classDto, Class.class);

        Class classCreate = classService.createClass(classRequest);

        ClassDto classResonse = modelMapper.map(classCreate, ClassDto.class);

        return new ResponseEntity<ClassDto>(classResonse, HttpStatus.CREATED);
    }

    @PutMapping("/{classId}")
    public ResponseEntity<ClassDto> updateClass(@PathVariable Long classId, @RequestBody ClassDto classDto) {

        Class classRequest = modelMapper.map(classDto, Class.class);

        Class classUpdate = classService.updateClass(classId, classRequest);

        ClassDto classResponse = modelMapper.map(classUpdate, ClassDto.class);

        return ResponseEntity.ok().body(classResponse);
    }

    @DeleteMapping("/{classId}")
    public ResponseEntity<Map<String, Boolean>> deleteClass(@PathVariable Long classId) {
        classService.deleteClass(classId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
