package com.sep490.g49.shibadekiru.controller.lecture;


import com.sep490.g49.shibadekiru.dto.ClassDto;
import com.sep490.g49.shibadekiru.dto.ClassWorkDto;
import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.ClassWork;
import com.sep490.g49.shibadekiru.service.IClassService;
import com.sep490.g49.shibadekiru.service.IClassWorkService;
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
@CrossOrigin(origins = "*")
@RequestMapping("/api/lecture/class")
public class LectureManageClassWorkController {

    @Autowired
    private IClassWorkService iClassWorkService;

    @Autowired
    private IClassService iClassService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}/class-work")
    public List<ClassWorkDto> getAllClassWorkByClass(@PathVariable (name = "id") Long classId) {
        Class classResponse = iClassService.getClassById(classId);
        return iClassWorkService.getClassWorkByClass(classResponse).stream().map(classWork -> modelMapper.map(classWork, ClassWorkDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/class-work/{id}")
    public ResponseEntity<ClassWorkDto> getClassWorkById(@PathVariable (name = "id") Long id) {
        ClassWork classWork = iClassWorkService.getClassWorkById(id);
        ClassWorkDto classWorkResponse = modelMapper.map(classWork, ClassWorkDto.class);

        return ResponseEntity.ok().body(classWorkResponse);
    }

    @PostMapping("/{classId}/class-work")
    public ResponseEntity<ClassWorkDto> createClassWork( @PathVariable (name = "classId") Long classId, @RequestBody ClassWorkDto classWorkDto) {
        classWorkDto.setMyCId(classId);
        ClassWorkDto classWorkResponse = iClassWorkService.createClassWork(classWorkDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(classWorkResponse);

    }


    @PutMapping("/class-work/{id}")
    public ResponseEntity<ClassWorkDto> updateClassWork(@PathVariable (name = "id") Long id , @RequestBody ClassWorkDto classWorkDto) {

        ClassWork classWorkRequest = modelMapper.map(classWorkDto, ClassWork.class);

        ClassWork classWork = iClassWorkService.updateClassWork(id, classWorkRequest);

        ClassWorkDto classWorkResponse = modelMapper.map(classWork, ClassWorkDto.class);

        return ResponseEntity.ok().body(classWorkResponse);
    }

    @DeleteMapping("/class-work/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteClassWork(@PathVariable(name = "id") Long id) {
        iClassWorkService.deleteClassWork(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/class-work/update-is-locked/{id}")
    public ResponseEntity<Void> updateIsLocked(@PathVariable Long id) {
        iClassWorkService.updateIsLocked(id);
        return ResponseEntity.ok().build();
    }



}
