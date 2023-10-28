package com.sep490.g49.shibadekiru.controller.admin;

import com.sep490.g49.shibadekiru.dto.KaiwaDto;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Kaiwa;
import com.sep490.g49.shibadekiru.service.IKaiwaService;
import com.sep490.g49.shibadekiru.service.ILessonService;
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
@RequestMapping("/api/admin/lesson")
public class AdminManageKaiwaController {

    @Autowired
    private IKaiwaService iKaiwaService;

    @Autowired
    private ILessonService iLessonService;

    @Autowired
    private ModelMapper map;

    @GetMapping("/{id}/kaiwa")
    public List<KaiwaDto> getKaiwaByLesson(@PathVariable(name = "id") Long lessonId) {
        Lesson lessonResponse = iLessonService.getLessonById(lessonId);
        return iKaiwaService.getKaiwaPartByLesson(lessonResponse).stream().map(kaiwa ->map.map(kaiwa, KaiwaDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/kaiwa/{id}")
    public ResponseEntity<KaiwaDto> getKaiwaById(@PathVariable(name = "id") Long id) {
        Kaiwa kaiwa = iKaiwaService.getKaiwaById(id);

        // convert entity to DTO
        KaiwaDto kaiwaResponse = map.map(kaiwa, KaiwaDto.class);

        return ResponseEntity.ok().body(kaiwaResponse);
    }

    @PostMapping( "/{id}/kaiwa")
    public ResponseEntity<KaiwaDto> createKaiwa(@RequestBody KaiwaDto kaiwaDto, @PathVariable(name = "id") Long lessonId) {
        kaiwaDto.setLessonId(iLessonService.getLessonById(lessonId).getLessonId());
        // convert DTO to entity
        Kaiwa kaiwaRequest = map.map(kaiwaDto, Kaiwa.class);

        Kaiwa kaiwa = iKaiwaService.createKaiwa(kaiwaRequest);

        // convert entity to DTO
        KaiwaDto kaiwaResponse = map.map(kaiwa, KaiwaDto.class);

        return new ResponseEntity<KaiwaDto>(kaiwaResponse, HttpStatus.CREATED);
    }

    // change the request for DTO
    // change the response for DTO
    @PutMapping("/kaiwa/{id}")
    public ResponseEntity<KaiwaDto> updateKaiwa(@PathVariable long id, @RequestBody KaiwaDto kaiwaDto) {

        // convert DTO to Entity
        Kaiwa kaiwaRequest = map.map(kaiwaDto, Kaiwa.class);

        Kaiwa kaiwa = iKaiwaService.updateKaiwa(id, kaiwaRequest);

        // entity to DTO
        KaiwaDto kaiwaResponse = map.map(kaiwa, KaiwaDto.class);

        return ResponseEntity.ok().body(kaiwaResponse);
    }

    @DeleteMapping("/kaiwa/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteKaiwa(@PathVariable(name = "id") Long id) {
        iKaiwaService.deleteKaiwa(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
