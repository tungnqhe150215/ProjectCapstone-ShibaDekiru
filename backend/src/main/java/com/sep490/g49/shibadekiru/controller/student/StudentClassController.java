package com.sep490.g49.shibadekiru.controller.student;

import com.sep490.g49.shibadekiru.dto.ClassDto;
import com.sep490.g49.shibadekiru.dto.TestAssignDto;
import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
}
