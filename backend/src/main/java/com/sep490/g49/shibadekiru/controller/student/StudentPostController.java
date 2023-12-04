package com.sep490.g49.shibadekiru.controller.student;

import com.sep490.g49.shibadekiru.dto.CommentDto;
import com.sep490.g49.shibadekiru.dto.PostDto;
import com.sep490.g49.shibadekiru.dto.UserAccountDto;
import com.sep490.g49.shibadekiru.entity.Comment;
import com.sep490.g49.shibadekiru.entity.Post;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import com.sep490.g49.shibadekiru.service.ICommentService;
import com.sep490.g49.shibadekiru.service.IPostService;
import com.sep490.g49.shibadekiru.service.IUserAccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/post")
public class StudentPostController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IPostService iPostService;

    @Autowired
    private IUserAccountService iUserAccountService;

    @Autowired
    private ICommentService iCommentService;

    @GetMapping()
    public List<PostDto> getAllPosts() {
        return iPostService.getAllPosts().stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable (name = "id") Long postId){
        Post post = iPostService.getPostById(postId);

        // convert entity to DTO
        PostDto postResponse = modelMapper.map(post, PostDto.class);

        return  ResponseEntity.ok().body(postResponse);
    }

    @GetMapping("/{id}/comment")
    public List<CommentDto> getAllCommentByPost(@PathVariable(name = "id") Long postId) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Post post = iPostService.getPostById(postId);
        System.out.println("Comment từ post nè : " +  postId);
        return iCommentService.getCommentPartByPost(post).stream().map(comment -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/{id}/comment/{userId}")
    public List<CommentDto> getAllCommentByUserAccount(@PathVariable(name = "id") Long postId, @PathVariable(name = "userId") Long userId) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        System.out.println("Member Id of getAllCommentByUserAccount :  " + userId);
        UserAccount userAccount = iUserAccountService.getUserAccountById(userId);
        //System.out.println("User account :" + userAccount);
        return iCommentService.getCommentByUserAccount(userAccount).stream().map(comment -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
    }


    @PostMapping("/{id}/comment/{userId}")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "id") Long postId, @PathVariable(name = "userId") Long userId, @RequestBody CommentDto commentDto) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        commentDto.setPostId(postId);
        commentDto.setUserAccountId(userId);

        System.out.println("Member Id in add: " + commentDto.getUserAccountId());

        CommentDto commentResponse = iCommentService.createComment(commentDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponse);
    }

    @PutMapping("/{id}/comment/{userId}/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable (name = "id") Long postId, @PathVariable(name = "userId") Long userId
    , @PathVariable(name = "commentId") Long commentId, @RequestBody CommentDto commentDto) {
        Comment commentRequest = modelMapper.map(commentDto, Comment.class);

        Comment comment = iCommentService.updateComment( postId, userId, commentId, commentRequest);

        CommentDto commentResponse = modelMapper.map(comment, CommentDto.class);

        return ResponseEntity.ok().body(commentResponse);
    }

    @DeleteMapping("/{id}/comment/{userId}/{commentId}")
    public ResponseEntity<Map<String, Boolean>> deleteComment(@PathVariable Long commentId) {
        iCommentService.deleteComment(commentId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/4lastest")
    public List<PostDto> List4TopPost(){
        return iPostService.findTop4ByOrderByCreatedAtDesc().stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

}
