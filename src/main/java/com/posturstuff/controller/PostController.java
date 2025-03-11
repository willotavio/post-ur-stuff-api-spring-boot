package com.posturstuff.controller;

import com.posturstuff.dto.posts.PostAddDto;
import com.posturstuff.dto.posts.PostUpdateDto;
import com.posturstuff.dto.posts.PostViewDto;
import com.posturstuff.enums.PostVisibility;
import com.posturstuff.model.UserPrincipal;
import com.posturstuff.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/posturstuff-api/post")
public class PostController {

    @Autowired
    public PostService postService;

    @GetMapping
    public ResponseEntity<Map<String, List<PostViewDto>>> getAllPublic(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(defaultValue = "createdAt") String field
    ) {
        HttpStatus responseStatus = HttpStatus.OK;
        Map<String, List<PostViewDto>> responseBody = new HashMap<>();
        List<PostViewDto> posts = postService.getByVisibility(PostVisibility.PUBLIC.toString(), page, size, sortDirection, field);
        responseBody.put("posts", posts);
        return ResponseEntity.status(responseStatus).body(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") String id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        HttpStatus responseStatus = null;
        Map<String, Object> responseBody = new HashMap<>();
        Optional<PostViewDto> post = postService.getById(id, userPrincipal.getId());
        if(post.isEmpty()) {
            responseStatus = HttpStatus.NOT_FOUND;
            responseBody.put("message", "Post not found");
        }
        else {
            responseStatus = HttpStatus.OK;
            responseBody.put("post", post.get());
        }
        return ResponseEntity.status(responseStatus).body(responseBody);
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, List<PostViewDto>>> getOwnPosts(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(defaultValue = "createdAt") String field
    ) {
        HttpStatus responseStatus = HttpStatus.OK;
        Map<String, List<PostViewDto>> responseBody = new HashMap<>();
        List<PostViewDto> posts = postService.getByUserId(userPrincipal.getId(), page, size, sortDirection, field);
        responseBody.put("posts", posts);
        return ResponseEntity.status(responseStatus).body(responseBody);
    }

    @GetMapping("/me/{visibility}")
    public ResponseEntity<Map<String, List<PostViewDto>>> getOwnPostsWithVisibility(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable("visibility") String visibility,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(defaultValue = "createdAt") String field
    ) {
        HttpStatus responseStatus = HttpStatus.OK;
        Map<String, List<PostViewDto>> responseBody = new HashMap<>();
        List<PostViewDto> posts = postService.getByUserIdAndVisibility(userPrincipal.getId(), visibility.toUpperCase(), page, size, sortDirection, field);
        responseBody.put("posts", posts);
        return ResponseEntity.status(responseStatus).body(responseBody);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, List<PostViewDto>>> getPublicByUserId(
            @PathVariable("userId") String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(defaultValue = "createdAt") String field
            ) {
        HttpStatus responseStatus = HttpStatus.OK;
        Map<String, List<PostViewDto>> responseBody = new HashMap<>();
        List<PostViewDto> posts = postService.getByUserIdAndVisibility(userId, PostVisibility.PUBLIC.toString(), page, size, sortDirection, field);
        responseBody.put("posts", posts);
        return ResponseEntity.status(responseStatus).body(responseBody);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody @Valid PostAddDto postAddDto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        HttpStatus responseStatus = null;
        Map<String, Object> responseBody = new HashMap<>();
        Optional<PostViewDto> post = postService.add(postAddDto, userPrincipal.getId());
        if(post.isEmpty()) {
            responseStatus = HttpStatus.BAD_REQUEST;
            responseBody.put("message", "User not found");
        }
        else {
            responseStatus = HttpStatus.OK;
            responseBody.put("post", post.get());
        }
        return ResponseEntity.status(responseStatus).body(responseBody);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateById(@PathVariable("id") String postId, @RequestBody @Valid PostUpdateDto postUpdateDto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        HttpStatus responseStatus = null;
        Map<String, Object> responseBody = new HashMap<>();
        Optional<PostViewDto> result = postService.updateById(postId, postUpdateDto, userPrincipal.getId());
        if(result.isEmpty()) {
            responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseBody.put("message", "Unexpected error occurred");
        }
        else {
            responseStatus = HttpStatus.OK;
            responseBody.put("post", result.get());
        }
        return ResponseEntity.status(responseStatus).body(responseBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") String postId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        HttpStatus responseStatus = null;
        Map<String, Object> responseBody = new HashMap<>();
        Optional<PostViewDto> result = postService.deleteById(postId, userPrincipal.getId());
        if(result.isEmpty()) {
            responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseBody.put("message", "Unexpected error occurred");
        }
        else {
            responseStatus = HttpStatus.OK;
            responseBody.put("post", result.get());
        }
        return ResponseEntity.status(responseStatus).body(responseBody);
    }

}
