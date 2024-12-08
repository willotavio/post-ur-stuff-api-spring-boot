package com.posturstuff.repository;

import com.posturstuff.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByUserId(String userId);
    List<Post> findByVisibility(String visibility);
    List<Post> findByUserIdAndVisibility(String userId, String visibility);
}
