package com.posturstuff.repository;

import com.posturstuff.dto.posts.PostFiltersDto;
import com.posturstuff.enums.PostVisibility;
import com.posturstuff.model.Post;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CustomPostRepositoryImpl implements CustomPostRepository {

    private final MongoTemplate mongoTemplate;

    public CustomPostRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Post> findByFilters(PostFiltersDto filter) {
        List<Criteria> criteria = new ArrayList<>();

        if(filter.id() != null && !filter.id().isEmpty()) {
            criteria.add(Criteria.where("id").is(filter.id()));
        }
        if(filter.userId() != null && !filter.userId().isEmpty()) {
            criteria.add(Criteria.where("userId").is(filter.userId()));
        }
        if(filter.createdAtMin() != null) {
            criteria.add(Criteria.where("createdAt").gte(filter.createdAtMin()));
        }
        if(filter.createdAtMax() != null) {
            criteria.add(Criteria.where("createdAt").lte(filter.createdAtMax()));
        }
        if(filter.editedAtMin() != null) {
            criteria.add(Criteria.where("editedAt").gte(filter.editedAtMin()));
        }
        if(filter.editedAtMax() != null) {
            criteria.add(Criteria.where("editedAt").lte(filter.editedAtMax()));
        }
        if(filter.visibility() != null) {
            List<Criteria> orCriteria = new ArrayList<>();
            for(Integer v : filter.visibility()) {
                orCriteria.add(Criteria.where("visibility").is(PostVisibility.valueOf(v)));
            }
            criteria.add(new Criteria().orOperator(
                    orCriteria.toArray(new Criteria[0])
            ));
        }

        Query query = new Query();
        if(!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }

        return mongoTemplate.find(query, Post.class);
    }
}
