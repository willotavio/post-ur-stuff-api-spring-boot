package com.posturstuff.controller;

import com.posturstuff.dto.posts.PostViewDto;
import com.posturstuff.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

}
