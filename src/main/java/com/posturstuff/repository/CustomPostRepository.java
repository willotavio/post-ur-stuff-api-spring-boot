package com.posturstuff.repository;

import com.posturstuff.dto.posts.PostFiltersDto;
import com.posturstuff.dto.posts.PostViewDto;
import com.posturstuff.model.Post;

import java.util.List;

public interface CustomPostRepository {
    List<Post> findByFilters(PostFiltersDto postFiltersDto);
}
