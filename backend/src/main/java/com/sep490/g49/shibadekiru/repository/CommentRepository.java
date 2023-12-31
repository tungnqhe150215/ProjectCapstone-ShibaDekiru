package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.dto.CommentDto;
import com.sep490.g49.shibadekiru.dto.UserAccountDto;
import com.sep490.g49.shibadekiru.entity.Comment;
import com.sep490.g49.shibadekiru.entity.Post;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByPost(Post post, Pageable pageable);

    List<Comment> findByUserAccount(UserAccount userAccount);


}
