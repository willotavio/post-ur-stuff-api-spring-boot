package com.posturstuff.controller;

import com.posturstuff.dto.posts.PostAddDto;
import com.posturstuff.dto.posts.PostUpdateDto;
import com.posturstuff.dto.posts.PostViewDto;
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
    public ResponseEntity<List<PostViewDto>> getAll() {
        List<PostViewDto> posts = postService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") String id) {
        HttpStatus responseStatus = null;
        Map<String, Object> responseBody = new HashMap<>();
        Optional<PostViewDto> post = postService.getById(id);
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
