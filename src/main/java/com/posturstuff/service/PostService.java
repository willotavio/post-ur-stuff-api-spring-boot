package com.posturstuff.service;

import com.posturstuff.dto.posts.PostViewDto;
import com.posturstuff.mapper.PostMapper;
import com.posturstuff.model.Post;
import com.posturstuff.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    public PostRepository postRepository;

    @Autowired
    public PostMapper postMapper;

    public List<PostViewDto> getAll() {
        List<Post> posts = postRepository.findAll();
        List<PostViewDto> postsDto = new ArrayList<>();
        for(Post post : posts) {
            postsDto.add(postMapper.postToPostViewDto(post));
        }
        return postsDto;
    }

    public Optional<PostViewDto> getById(String id) {
        Optional<Post> post = postRepository.findById(id);
        if(post.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(postMapper.postToPostViewDto(post.get()));
    }

}
