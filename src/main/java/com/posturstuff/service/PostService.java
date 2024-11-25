package com.posturstuff.service;

import com.posturstuff.dto.posts.PostAddDto;
import com.posturstuff.dto.posts.PostUpdateDto;
import com.posturstuff.dto.posts.PostViewDto;
import com.posturstuff.enums.PostVisibility;
import com.posturstuff.exception.authorization.UnauthorizedException;
import com.posturstuff.exception.post.PostNotFoundException;
import com.posturstuff.mapper.PostMapper;
import com.posturstuff.model.Post;
import com.posturstuff.model.Users;
import com.posturstuff.repository.PostRepository;
import com.posturstuff.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    public PostRepository postRepository;

    @Autowired
    public PostMapper postMapper;

    @Autowired
    public UserRepository userRepository;

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

    public Optional<PostViewDto> add(PostAddDto postAddDto, String userId) {
        Optional<Users> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            return Optional.empty();
        }
        Post newPost = postRepository.insert(
                new Post(
                    postAddDto.content(),
                    postAddDto.images(),
                    user.get(),
                    LocalDate.now(),
                    PostVisibility.valueOf(postAddDto.visibility())
            )
        );
        return Optional.of(postMapper.postToPostViewDto(newPost));
    }

    public Optional<PostViewDto> updateById(String postId, PostUpdateDto postUpdateDto, String userId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if(postOptional.isEmpty()) {
            throw new PostNotFoundException("Post with id " + postId + " not found");
        }
        if(!postOptional.get().getUser().getId().equals(userId)) {
            throw new UnauthorizedException("User with id " + userId + " is not authorized");
        }
        Post post = postOptional.get();
        if(postUpdateDto.content() != null) {
            post.setContent(postUpdateDto.content());
        }
        if(postUpdateDto.images() != null) {
            post.setImages(postUpdateDto.images());
        }
        if(postUpdateDto.visibility() > 0) {
            post.setVisibility(PostVisibility.valueOf(postUpdateDto.visibility()));
        }
        post.setEditedAt(LocalDate.now());
        Post newPost = postRepository.save(post);
        return Optional.of(postMapper.postToPostViewDto(newPost));
    }

    public Optional<PostViewDto> deleteById(String postId, String userId) {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()) {
            throw new PostNotFoundException("Post with id " + postId + " not found");
        }
        if(!post.get().getUser().getId().equals(userId)) {
            throw new UnauthorizedException("User with id " + userId + " is not authorized");
        }
        postRepository.deleteById(postId);
        return Optional.of(postMapper.postToPostViewDto(post.get()));
    }

}
