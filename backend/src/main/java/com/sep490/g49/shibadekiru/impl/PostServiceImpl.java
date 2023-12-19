package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.dto.PostDto;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.Post;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.LecturersRepository;
import com.sep490.g49.shibadekiru.repository.PostRepository;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
import com.sep490.g49.shibadekiru.service.IPostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements IPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LecturersRepository lecturersRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GoogleDriveService googleDriveService;

    @Override
    public List<Post> getAllPosts() {

        List<Post> postList = postRepository.findAll();
        if (!postList.isEmpty()) {
            return postList;
        } else {
            throw new ResourceNotFoundException("List Post is blank.");
        }

    }

//    public Page<Post> getPaginatedItems(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return postRepository.findByIsEnabled(true, pageable);
//    }

    public Page<Post> getPaginatedItems(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postRepository.findByIsEnabled(true, pageable);

        List<Post> modifiedPosts = postPage.getContent().stream()
                .peek(data -> data.setImage(googleDriveService.getFileUrl(data.getImage())))
                .collect(Collectors.toList());

        return new PageImpl<>(modifiedPosts, pageable, postPage.getTotalElements());
    }

    @Override
    public List<Post> getAllPostByIsEnable() {
        List<Post> postList = postRepository.findAll();

        List<Post> openPosts = postList.stream()
                .filter(post -> post.getIsEnabled().equals(true))
                .collect(Collectors.toList());

        if (!openPosts.isEmpty()) {
            return openPosts.stream().peek(data ->
                    data.setImage(googleDriveService.getFileUrl(data.getImage()))
            ).collect(Collectors.toList());
        } else {
            throw new ResourceNotFoundException("No open posts found.");
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
        List<Post> postList = postRepository.findTop4ByOrderByCreatedAtDesc();

        List<Post> openPosts = postList.stream()
                .filter(post -> post.getIsEnabled().equals(true))
                .collect(Collectors.toList());

        if (!openPosts.isEmpty()) {
            return openPosts.stream().peek(data -> {
                if (data.getImage().length() > 0 && !data.getImage().equals("")) {
                    data.setImage(googleDriveService.getFileUrl(data.getImage()));
                } else {
                    data.setImage(data.getImage());
                }
            }).collect(Collectors.toList());

        } else {
            throw new ResourceNotFoundException("No open posts found.");
        }

    }

    @Override
    public PostDto createPost(PostDto postDTO) {
        // Convert PostDTO to Post entity
        Post post = modelMapper.map(postDTO, Post.class);

        Lectures lecture = lecturersRepository.findById(postDTO.getLectureId())
                .orElseThrow(() -> new ResourceNotFoundException("Lecture not found"));

        post.setLecture(lecture);
        post.setImage(postDTO.getImage());
        post.setIsEnabled(postDTO.getIsEnabled());
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

            if (updatedPostDto.getImage().length() > 0) {

                googleDriveService.deleteFile(post.getImage());
                System.out.println("File đã xóa : " + post.getImage());
                post.setImage(updatedPostDto.getImage());
            } else {

                post.setImage(post.getImage());

            }

            Post updated = postRepository.save(post);

            return modelMapper.map(updated, PostDto.class);
        } else {
            throw new ResourceNotFoundException("Post not found " + postId);
        }
    }


    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

        if (post.getImage() != null) {
            googleDriveService.deleteFile(post.getImage());
            System.out.println("Đã vào đây.");
        }

        postRepository.delete(post);

    }

    @Override
    public Post getPostById(Long id) {

        Post post = postRepository.findById(id).orElse(null);

        if (post == null) {
            throw new ResourceNotFoundException("Post not found with id: " + id);
        }

        return post;
    }


}
