package com.udith.comment_adding_service.repository;

import com.udith.comment_adding_service.model.CommentList;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentAddingRepository extends MongoRepository<CommentList, String> {
    CommentList findById(ObjectId id);
}