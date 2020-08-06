package com.example.springsecurity.repository;

import com.example.springsecurity.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @author ClowLAY
 * create date 2020/5/23
 */
public interface UserRepository extends MongoRepository<User, ObjectId> {

    Optional<User> findByUsername(String username);
}
