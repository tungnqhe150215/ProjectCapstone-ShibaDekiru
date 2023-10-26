package com.sep490.g49.shibadekiru.controller;

import com.sep490.g49.shibadekiru.dto.ClassDto;
import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.service.IClassService;
import com.sep490.g49.shibadekiru.util.RandomStringGeneratorService;
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
@CrossOrigin("*")
@RequestMapping("/api/admin/class")
public class AdminManageClassController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IClassService classService;

    @Autowired
    RandomStringGeneratorService randomStringGeneratorService;

    @GetMapping()
    public List<ClassDto> getAllClass() {
        return classService.getAllClass().stream().map(aClass -> modelMapper.map(aClass, ClassDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/{classId}")
    public ResponseEntity<ClassDto> getClassById(@PathVariable(name = "classId") long classId){
        Class aClass = classService.getClassById(classId);

        //convert Entity to DTO
        ClassDto classResponse = modelMapper.map(aClass, ClassDto.class);

        return ResponseEntity.ok().body(classResponse);
    }

    @PostMapping()
    public ResponseEntity<ClassDto> createClass(@RequestBody ClassDto classDto) {

        Class classRequest = modelMapper.map(classDto, Class.class);
        classRequest.setClassCode(randomStringGeneratorService.randomAlphaNumeric(7));

        Class aClass = classService.createClass(classRequest);

        ClassDto classResponse = modelMapper.map(aClass, ClassDto.class);

        return new ResponseEntity<ClassDto>(classResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{classId}")
    public ResponseEntity<ClassDto> updateClass(@PathVariable Long classId, @RequestBody ClassDto classDto){

        Class classRequest = modelMapper.map(classDto, Class.class);

        Class aClass = classService.updateClass(classId, classRequest);

        ClassDto classResponse = modelMapper.map(aClass, ClassDto.class);

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
