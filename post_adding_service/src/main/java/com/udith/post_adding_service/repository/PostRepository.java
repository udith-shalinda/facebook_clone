package com.udith.post_adding_service.repository;

import com.udith.post_adding_service.model.Post;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post,String>{
    Post findById(ObjectId id);
}