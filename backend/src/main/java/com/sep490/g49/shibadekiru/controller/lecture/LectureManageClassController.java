package com.sep490.g49.shibadekiru.controller.lecture;

import com.sep490.g49.shibadekiru.dto.ClassDto;
import com.sep490.g49.shibadekiru.dto.ClassStudentDto;
import com.sep490.g49.shibadekiru.dto.LecturesDto;
import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.service.IClassService;
import com.sep490.g49.shibadekiru.service.IClassStudentService;
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

    IClassStudentService iClassStudentService;

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
        LecturesDto lecturesDto = new LecturesDto();
        lecturesDto.setLectureId(lectureId);
        classDto.setLecture(lecturesDto);

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

    @PutMapping("/class/update-is-locked/{id}")
    public ResponseEntity<Void> updateIsLocked(@PathVariable Long id) {
        classService.updateIsLocked(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/class/{classId}/member")
    public List<ClassStudentDto> getAllClassMember(@PathVariable (name = "classId") Long classId){

        Class aClass = classService.getClassById(classId);

        return iClassStudentService.getClassStudentByClass(aClass).stream().map(classStudent -> modelMapper.map(classStudent, ClassStudentDto.class)).collect(Collectors.toList());
    }
}
