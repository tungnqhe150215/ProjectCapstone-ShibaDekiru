package com.sep490.g49.shibadekiru.controller.student;

import com.sep490.g49.shibadekiru.dto.ClassDto;
import com.sep490.g49.shibadekiru.dto.ClassStudentDto;
import com.sep490.g49.shibadekiru.dto.ListeningDto;
import com.sep490.g49.shibadekiru.dto.TestAssignDto;
import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.ClassStudent;
import com.sep490.g49.shibadekiru.entity.Student;
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
public class StudentClassController {

    @Autowired
    private IClassService iClassService;

    @Autowired
    private IClassStudentService iClassStudentService;

    @Autowired
    private IClassWorkService iClassWorkService;

    @Autowired
    private IStudentClassWorkService iStudentClassWorkService;

    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private ModelMapper map;

    @GetMapping("/class/{id}")
    public ClassDto getClassById(@PathVariable("id") Long id) {

        ClassDto classDto = map.map( iClassService.getClassById(id),ClassDto.class);

        return classDto;
    }

    @GetMapping("/class/list")
    public List<ClassStudentDto> getClassByStudent(@RequestParam("studentId") Long studentId) {

        Student student = iStudentService.getStudentByStudentId(studentId);

        return iClassStudentService.getClassStudentByStudent(student).stream().map(classStudent -> map.map(classStudent,ClassStudentDto.class)).collect(Collectors.toList());
    }

    @PostMapping("/class/join")
    public ResponseEntity<ClassDto> joinClassByClassCode(@RequestParam("studentId") Long studentId, @RequestParam("code") String code) {

        Class aClass = iClassService.getClassByCode(code);

        ClassStudent classStudent = new ClassStudent();
        classStudent.setStudent(iStudentService.getStudentByStudentId(studentId));
        classStudent.setBelongClass(aClass);
        iClassStudentService.createClassStudent(classStudent);

        ClassDto responseClass = map.map(aClass, ClassDto.class);
        return new ResponseEntity<ClassDto>(responseClass, HttpStatus.CREATED);
    }
}
