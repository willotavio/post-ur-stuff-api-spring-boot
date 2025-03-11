package com.posturstuff.repository;

import com.posturstuff.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    Page<Post> findByUserId(String userId, Pageable pageable);
    Page<Post> findByVisibility(String visibility, Pageable pageable);
    Page<Post> findByUserIdAndVisibility(String userId, String visibility, Pageable pageable);
}
