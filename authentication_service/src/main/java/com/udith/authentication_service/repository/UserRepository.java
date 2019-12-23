package com.udith.authentication_service.repository;

import com.udith.authentication_service.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String>{
    
}