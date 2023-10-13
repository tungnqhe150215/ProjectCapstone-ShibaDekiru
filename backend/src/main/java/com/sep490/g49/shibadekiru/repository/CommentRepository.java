package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
