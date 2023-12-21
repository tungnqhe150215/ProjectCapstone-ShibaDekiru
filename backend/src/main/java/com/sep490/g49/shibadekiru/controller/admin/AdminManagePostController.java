package com.sep490.g49.shibadekiru.controller.admin;

import com.sep490.g49.shibadekiru.dto.LecturesDto;
import com.sep490.g49.shibadekiru.dto.PostDto;
import com.sep490.g49.shibadekiru.entity.Post;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
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
@RequestMapping("/api/admin")
public class AdminManagePostController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IPostService iPostService;

    @Autowired
    private GoogleDriveService googleDriveService;

    @GetMapping("/post")
    public List<PostDto> getAllPosts() {
        return iPostService.getAllPosts().stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/lecture")
    public List<LecturesDto> getAllLectuer() {
        return iPostService.getAllLectures().stream().map(post -> modelMapper.map(post, LecturesDto.class)).collect(Collectors.toList());
    }
    @GetMapping("/post/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable (name = "id") Long postId) {
        Post post = iPostService.getPostById(postId);
        if (post.getImage().length() > 0 && !post.getImage().equals("")) {
            post.setImage(googleDriveService.getFileUrl(post.getImage()));
        }
        // convert entity to DTO
        PostDto postResponse = modelMapper.map(post, PostDto.class);

        return  ResponseEntity.ok().body(postResponse);
    }


    @PostMapping("/post")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDTO) {
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
