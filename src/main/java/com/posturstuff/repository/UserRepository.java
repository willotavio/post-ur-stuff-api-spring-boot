package com.posturstuff.repository;

import com.posturstuff.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<Users, String> {

    public Users findByUsername(String username);

}
