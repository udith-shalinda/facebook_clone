package com.udith.authentication_service.repository;

import com.udith.authentication_service.model.User;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String>{
    User findByEmail(String email);
    User findById(ObjectId id);
}