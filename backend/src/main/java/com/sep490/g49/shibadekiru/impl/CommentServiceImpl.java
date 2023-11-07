package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.dto.CommentDto;
import com.sep490.g49.shibadekiru.dto.UserAccountDto;
import com.sep490.g49.shibadekiru.entity.Comment;
import com.sep490.g49.shibadekiru.entity.Post;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.CommentRepository;
import com.sep490.g49.shibadekiru.repository.PostRepository;
import com.sep490.g49.shibadekiru.repository.UserAccountRepository;
import com.sep490.g49.shibadekiru.service.ICommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> getCommentPartByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    @Override
    public List<Comment> getCommentByUserAccount(UserAccount userAccount) {
        return commentRepository.findByUserAccount(userAccount);
    }


    @Override
    public CommentDto createComment(CommentDto commentDto) {

            Comment comment = modelMapper.map(commentDto, Comment.class);
            comment.setContent(comment.getContent());
            comment.setCreatedAt(LocalDateTime.now());
            Comment savedComment = commentRepository.save(comment);

            return modelMapper.map(savedComment, CommentDto.class);

    }

    @Override
    public Comment updateComment(Long postId, Long userId, Long commentId, Comment updatecomment) {
        Optional<Post> existingPost = postRepository.findById(postId);
        Optional<UserAccount> existingUserAccount = userAccountRepository.findById(userId);
        Optional<Comment> existingComment = commentRepository.findById(commentId);

        if (existingUserAccount.isPresent() && existingComment.isPresent() && existingPost.isPresent()) {
            Post post = existingPost.get();
            UserAccount userAccount = existingUserAccount.get();
            Comment comment = existingComment.get();

            comment.setContent(updatecomment.getContent());
            comment.setCreatedAt(LocalDateTime.now());
            comment.setPost(post);
            comment.setUserAccount(userAccount);

            return commentRepository.save(comment);
        }else {
            throw new ResourceNotFoundException("Comment not found with id: " + commentId);
        }
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));
        commentRepository.delete(comment);
    }

    @Override
    public Comment getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);

        if (comment == null) {
            throw new ResourceNotFoundException("Comment not found with id: " + commentId);
        }
        return comment;
    }
}
