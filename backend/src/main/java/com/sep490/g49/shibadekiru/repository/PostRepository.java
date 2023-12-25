package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByLecture(Lectures lectures);
    List<Post> findTop4ByIsEnabledTrueOrderByCreatedAtDesc();


    Page<Post> findByIsEnabledAndPostContentContainingIgnoreCase(boolean isEnabled, Pageable pageable, String keyword);
}
