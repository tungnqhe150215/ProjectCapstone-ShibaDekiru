package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.dto.PostDto;
import com.sep490.g49.shibadekiru.entity.Comment;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.Post;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.LecturersRepository;
import com.sep490.g49.shibadekiru.repository.PostRepository;
import com.sep490.g49.shibadekiru.service.IPostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements IPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LecturersRepository lecturersRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<Post> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        if (!postList.isEmpty()) {
            return postList;
        }
        else {
            throw new ResourceNotFoundException("List Post is blank.");
        }
    }

    @Override
    public List<Post> getPostPartByLecture(Lectures lectures) {
        return postRepository.findByLecture(lectures);
    }

    @Override
    public List<Lectures> getAllLectures() {
        return lecturersRepository.findAll();
    }

    @Override
    public List<Post> findTop4ByOrderByCreatedAtDesc() {
        return postRepository.findTop4ByOrderByCreatedAtDesc();
    }

    @Override
    public PostDto createPost(PostDto postDTO) {
        // Convert PostDTO to Post entity
        Post post = modelMapper.map(postDTO, Post.class);

        Lectures lecture = lecturersRepository.findById(postDTO.getLectureId())
                .orElseThrow(() -> new ResourceNotFoundException("Lecture not found"));

        post.setLecture(lecture);
        post.setCreatedAt(LocalDateTime.now());

        // Save the post
        Post savedPost = postRepository.save(post);

        // Convert the saved Post entity back to PostDTO
        PostDto savedPostDTO = modelMapper.map(savedPost, PostDto.class);

        return savedPostDTO;
    }

    @Override
    public PostDto updatePost(Long postId, PostDto updatedPostDto) {

        Optional<Post> existingPost = postRepository.findById(postId);
        if (existingPost.isPresent()) {
            Post post = existingPost.get();


            post.setPostContent(updatedPostDto.getPostContent());
            post.setDescription(updatedPostDto.getDescription());
            post.setCreatedAt(LocalDateTime.now());
            post.setIsEnabled(updatedPostDto.getIsEnabled());
            Post updated = postRepository.save(post);

            return modelMapper.map(updated, PostDto.class);
        } else {
            throw new ResourceNotFoundException("Post not found " + postId);
        }
    }


    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        postRepository.delete(post);

    }

    @Override
    public Post getPostById(Long id) {

        Post post =  postRepository.findById(id).orElse(null);

        if (post == null) {
            throw new ResourceNotFoundException("Post not found with id: " + id);
        }

        return post;
    }
}
