package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.dto.PostDto;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.Post;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.PostServiceImpl;
import com.sep490.g49.shibadekiru.repository.LecturersRepository;
import com.sep490.g49.shibadekiru.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private LecturersRepository lecturersRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void getAllPosts_ReturnsListOfPosts() {
//        // Arrange
//        List<Post> mockPosts = new ArrayList<>();
//        when(postRepository.findAll()).thenReturn(mockPosts);
//
//        // Act
//        List<Post> result = postService.getAllPosts();
//
//        // Assert
//        assertThat(result).isSameAs(mockPosts);
//    }

    @Test
    void getPostPartByLecture_ReturnsListOfPosts() {
        // Arrange
        Lectures mockLecture = new Lectures();
        List<Post> mockPosts = new ArrayList<>();
        when(postRepository.findByLecture(mockLecture)).thenReturn(mockPosts);

        // Act
        List<Post> result = postService.getPostPartByLecture(mockLecture);

        // Assert
        assertThat(result).isSameAs(mockPosts);
    }

    @Test
    void getAllLectures_ReturnsListOfLectures() {
        // Arrange
        List<Lectures> mockLectures = new ArrayList<>();
        when(lecturersRepository.findAll()).thenReturn(mockLectures);

        // Act
        List<Lectures> result = postService.getAllLectures();

        // Assert
        assertThat(result).isSameAs(mockLectures);
    }

    @Test
    void createPost_ReturnsCreatedPostDto() {
        // Arrange
        PostDto inputPostDto = new PostDto();
        Lectures mockLecture = new Lectures();
        Post mockSavedPost = new Post();
        PostDto mockSavedPostDto = new PostDto();
        when(modelMapper.map(inputPostDto, Post.class)).thenReturn(mockSavedPost);
        when(lecturersRepository.findById(inputPostDto.getLectureId())).thenReturn(Optional.of(mockLecture));
        when(postRepository.save(mockSavedPost)).thenReturn(mockSavedPost);
        when(modelMapper.map(mockSavedPost, PostDto.class)).thenReturn(mockSavedPostDto);

        // Act
        PostDto result = postService.createPost(inputPostDto);

        // Assert
        assertThat(result).isSameAs(mockSavedPostDto);
    }

    @Test
    void updatePost_WithExistingPost_ReturnsUpdatedPostDto() {
        // Arrange
        Long postId = 1L;
        PostDto updatedPostDto = new PostDto();
        updatedPostDto.setImage("");
        Optional<Post> existingPost = Optional.of(new Post());
        when(postRepository.findById(postId)).thenReturn(existingPost);
        when(postRepository.save(any())).thenReturn(existingPost.get());
        when(modelMapper.map(existingPost.get(), PostDto.class)).thenReturn(updatedPostDto);

        // Act
        PostDto result = postService.updatePost(postId, updatedPostDto);

        // Assert
        assertThat(result).isSameAs(updatedPostDto);
    }

    @Test
    void updatePost_WithNonexistentPost_ThrowsResourceNotFoundException() {
        // Arrange
        Long postId = 1L;
        PostDto updatedPostDto = new PostDto();
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> postService.updatePost(postId, updatedPostDto))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deletePost_WithExistingPost_DeletesPost() {
        // Arrange
        Long postId = 1L;
        Post existingPost = new Post();

        existingPost.setPostId(1L);
        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));

        // Act
        postService.deletePost(postId);

        // Assert
        verify(postRepository, times(1)).delete(existingPost);
    }

    @Test
    void deletePost_WithNonexistentPost_ThrowsResourceNotFoundException() {
        // Arrange
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> postService.deletePost(postId))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getPostById_WithExistingPost_ReturnsPost() {
        // Arrange
        Long postId = 1L;
        Post existingPost = new Post();
        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));

        // Act
        Post result = postService.getPostById(postId);

        // Assert
        assertThat(result).isSameAs(existingPost);
    }

    @Test
    void getPostById_WithNonexistentPost_ThrowsResourceNotFoundException() {
        // Arrange
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> postService.getPostById(postId))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
