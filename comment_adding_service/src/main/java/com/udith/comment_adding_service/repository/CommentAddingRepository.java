package com.udith.comment_adding_service.repository;

import com.udith.comment_adding_service.model.Comment;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentAddingRepository extends MongoRepository<Comment,String>{
    
}