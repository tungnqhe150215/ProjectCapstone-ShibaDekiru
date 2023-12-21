package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.dto.CommentDto;
import com.sep490.g49.shibadekiru.entity.Comment;
import com.sep490.g49.shibadekiru.entity.Post;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.CommentServiceImpl;
import com.sep490.g49.shibadekiru.repository.CommentRepository;
import com.sep490.g49.shibadekiru.repository.PostRepository;
import com.sep490.g49.shibadekiru.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllComments() {
        // Mock data
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        List<Comment> commentList = Arrays.asList(comment1, comment2);

        // Mock behavior
        when(commentRepository.findAll()).thenReturn(commentList);

        // Call the method
        List<Comment> result = commentService.getAllComments();

        // Assertions
        assertEquals(commentList.size(), result.size());

        // Verify interactions
        verify(commentRepository, times(1)).findAll();
    }

//    @Test
//    void getCommentPartByPost() {
//        // Mock data
//        Post post = new Post();
//        Comment comment1 = new Comment();
//        Comment comment2 = new Comment();
//        List<Comment> commentList = Arrays.asList(comment1, comment2);
//        Pageable pageable = new Pageable() ;
//
//
//        // Mock behavior
//        when(commentRepository.findByPost(post,)).thenReturn(commentList);
//
//        // Call the method
//        List<Comment> result = commentService.getCommentPartByPost(post);
//
//        // Assertions
//        assertEquals(commentList.size(), result.size());
//
//        // Verify interactions
//        verify(commentRepository, times(1)).findByPost(post);
//    }

    @Test
    void getCommentByUserAccount() {
        // Mock data
        UserAccount userAccount = new UserAccount();
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        List<Comment> commentList = Arrays.asList(comment1, comment2);

        // Mock behavior
        when(commentRepository.findByUserAccount(userAccount)).thenReturn(commentList);

        // Call the method
        List<Comment> result = commentService.getCommentByUserAccount(userAccount);

        // Assertions
        assertEquals(commentList.size(), result.size());

        // Verify interactions
        verify(commentRepository, times(1)).findByUserAccount(userAccount);
    }

//    @Test
//    void createComment() {
//        // Mock data
//        CommentDto commentDto = new CommentDto();
//        Comment comment = new Comment();
//
//        // Mock behavior
//        when(modelMapper.map(commentDto, Comment.class)).thenReturn(comment);
//        when(commentRepository.save(comment)).thenReturn(comment);
//
//        // Call the method
//        CommentDto result = commentService.createComment(commentDto);
//
//        // Assertions
//        assertNotNull(result);
//
//        // Verify interactions
//        verify(modelMapper, times(1)).map(commentDto, Comment.class);
//        verify(commentRepository, times(1)).save(comment);
//    }

    @Test
    void updateComment() {
        // Mock data
        Long postId = 1L;
        Long userId = 2L;
        Long commentId = 3L;
        Comment updateComment = new Comment();
        Post post = new Post();
        UserAccount userAccount = new UserAccount();
        Comment existingComment = new Comment();

        // Mock behavior
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(userAccountRepository.findById(userId)).thenReturn(Optional.of(userAccount));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(existingComment));
        when(commentRepository.save(existingComment)).thenReturn(existingComment);

        // Call the method
        Comment result = commentService.updateComment(postId, userId, commentId, updateComment);

        // Assertions
        assertNotNull(result);

        // Verify interactions
        verify(postRepository, times(1)).findById(postId);
        verify(userAccountRepository, times(1)).findById(userId);
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentRepository, times(1)).save(existingComment);
    }

    @Test
    void deleteComment() {
        // Mock data
        Long commentId = 1L;
        Comment comment = new Comment();

        // Mock behavior
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // Call the method
        commentService.deleteComment(commentId);

        // Verify interactions
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentRepository, times(1)).delete(comment);
    }

    @Test
    void getCommentById() {
        // Mock data
        Long commentId = 1L;
        Comment comment = new Comment();

        // Mock behavior
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // Call the method
        Comment result = commentService.getCommentById(commentId);

        // Assertions
        assertNotNull(result);

        // Verify interactions
        verify(commentRepository, times(1)).findById(commentId);
    }
}
