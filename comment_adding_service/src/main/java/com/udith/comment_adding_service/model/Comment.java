package com.udith.comment_adding_service.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment{
    @Id
    private ObjectId id;
    private String userId;
    private String commantContent; 
}