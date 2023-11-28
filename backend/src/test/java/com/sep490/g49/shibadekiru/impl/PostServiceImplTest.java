package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.dto.PostDto;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.Post;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.LecturersRepository;
import com.sep490.g49.shibadekiru.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private LecturersRepository lecturersRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    void getAllPosts() {
        // Mocking the behavior of postRepository.findAll() method
        List<Post> mockPosts = new ArrayList<>();
        Mockito.when(postRepository.findAll()).thenReturn(mockPosts);

        List<Post> result = postService.getAllPosts();

        assertNotNull(result);
        assertEquals(0, result.size()); // Assuming the mockPosts list is empty
    }

    @Test
    void getPostPartByLecture() {
        // Mocking the behavior of postRepository.findByLecture() method
        Lectures lecture = new Lectures();
        List<Post> mockPosts = new ArrayList<>();
        Mockito.when(postRepository.findByLecture(lecture)).thenReturn(mockPosts);

        List<Post> result = postService.getPostPartByLecture(lecture);

        assertNotNull(result);
        assertEquals(0, result.size()); // Assuming the mockPosts list is empty
    }

    @Test
    void getAllLectures() {
        // Mocking the behavior of lecturersRepository.findAll() method
        List<Lectures> mockLectures = new ArrayList<>();
        Mockito.when(lecturersRepository.findAll()).thenReturn(mockLectures);

        List<Lectures> result = postService.getAllLectures();

        assertNotNull(result);
        assertEquals(0, result.size()); // Assuming the mockLectures list is empty
    }

    @Test
    void createPost() {
        // Mocking the behavior of modelMapper.map() method
        PostDto postDto = new PostDto();
        Post post = new Post();
        Mockito.when(modelMapper.map(postDto, Post.class)).thenReturn(post);

        // Mocking the behavior of lecturersRepository.findById() method
        Long lectureId = 1L;
        Lectures lecture = new Lectures();
        Mockito.when(lecturersRepository.findById(lectureId)).thenReturn(Optional.of(lecture));

        // Mocking the behavior of postRepository.save() method
        Post savedPost = new Post();
        Mockito.when(postRepository.save(post)).thenReturn(savedPost);

        PostDto result = postService.createPost(postDto);

        assertNotNull(result);
        // Add more assertions as needed
    }

    @Test
    void updatePost() {
        Long postId = 1L;
        PostDto updatedPostDto = new PostDto();

        // Mocking the behavior of postRepository.findById() method
        Optional<Post> existingPost = Optional.of(new Post());
        Mockito.when(postRepository.findById(postId)).thenReturn(existingPost);

        // Mocking the behavior of modelMapper.map() method
        Post updatedPost = new Post();
        Mockito.when(modelMapper.map(updatedPostDto, Post.class)).thenReturn(updatedPost);

        // Mocking the behavior of postRepository.save() method
        Post savedPost = new Post();
        Mockito.when(postRepository.save(updatedPost)).thenReturn(savedPost);

        PostDto result = postService.updatePost(postId, updatedPostDto);

        assertNotNull(result);
        // Add more assertions as needed
    }

    @Test
    void deletePost() {
        Long postId = 1L;

        // Mocking the behavior of postRepository.findById() method
        Optional<Post> existingPost = Optional.of(new Post());
        Mockito.when(postRepository.findById(postId)).thenReturn(existingPost);

        assertDoesNotThrow(() -> postService.deletePost(postId));
    }

    @Test
    void getPostById() {
        Long postId = 1L;

        // Mocking the behavior of postRepository.findById() method
        Post expectedPost = new Post();
        Mockito.when(postRepository.findById(postId)).thenReturn(Optional.of(expectedPost));

        Post result = postService.getPostById(postId);

        assertNotNull(result);
        // Add more assertions as needed
    }
}
