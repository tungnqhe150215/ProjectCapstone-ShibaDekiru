package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.dto.CommentDto;
import com.sep490.g49.shibadekiru.dto.UserAccountDto;
import com.sep490.g49.shibadekiru.entity.Comment;
import com.sep490.g49.shibadekiru.entity.Post;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICommentService {

    List<Comment> getAllComments();


    List<Comment> getCommentPartByPost(Post post);

    List<Comment> getCommentByUserAccount(UserAccount userAccount);


    CommentDto createComment(CommentDto commentDto);

    Comment updateComment(Long postId, Long userId, Long commentId, Comment updatecomment);

    void deleteComment(Long commentId);

    Comment getCommentById(Long commentId);
}
