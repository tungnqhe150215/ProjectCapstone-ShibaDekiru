package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.dto.PostDto;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.Post;

import java.util.Collection;
import java.util.List;

public interface IPostService {

    List<Post> getAllPosts();

    List<Post> getAllPostByIsEnable();

    List<Post> getPostPartByLecture(Lectures lectures);

    PostDto createPost(PostDto post);

    PostDto updatePost(Long id, PostDto post);

    void deletePost(Long id);

    Post getPostById(Long id);

    List<Lectures> getAllLectures();

    List<Post> findTop4ByOrderByCreatedAtDesc();
}
