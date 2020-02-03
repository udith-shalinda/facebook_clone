package com.udith.story_service.repository;

import com.udith.story_service.model.Story;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StoryRepository extends MongoRepository<Story,String>{
    Story findById(ObjectId id);
}