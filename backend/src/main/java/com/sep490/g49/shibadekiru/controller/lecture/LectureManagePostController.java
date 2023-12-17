package com.sep490.g49.shibadekiru.controller.lecture;

import com.sep490.g49.shibadekiru.dto.PostDto;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.Post;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
import com.sep490.g49.shibadekiru.service.ILecturesService;
import com.sep490.g49.shibadekiru.service.IPostService;
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
@RequestMapping("/api/lecture")
public class LectureManagePostController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IPostService iPostService;

    @Autowired
    private ILecturesService iLecturesService;

    @Autowired
    private GoogleDriveService googleDriveService;


    @GetMapping("/{id}/post")
    public List<PostDto> getAllPostsByLecture(@PathVariable (name = "id") Long lectureId) {
        Lectures lecturesResponse = iLecturesService.getLectureById(lectureId);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return iPostService.getPostPartByLecture(lecturesResponse).stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long postId) {
        Post post = iPostService.getPostById(postId);
        post.setImage(googleDriveService.getFileUrl(post.getImage()));
        // convert entity to DTO
        PostDto postResponse = modelMapper.map(post, PostDto.class);

        return  ResponseEntity.ok().body(postResponse);
    }


    @PostMapping("/{id}/post")
    public ResponseEntity<PostDto> createPost(@PathVariable (name = "id") Long lectureId, @RequestBody PostDto postDTO) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        postDTO.setLectureId(iLecturesService.getLectureById(lectureId).getLectureId());
        PostDto savedPostDTO = iPostService.createPost(postDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPostDTO);
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable Long postId,
            @RequestBody PostDto updatedPostDto
    ) {

        PostDto updatedPost = iPostService.updatePost(postId, updatedPostDto);

        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePost(@PathVariable Long id) {
        iPostService.deletePost(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
