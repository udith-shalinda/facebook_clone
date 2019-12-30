package com.udith.image_service.repository;

import com.udith.image_service.model.Photo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<Photo, String> { }