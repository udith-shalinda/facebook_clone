package com.udith.post_adding_service.model;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> likeList = new ArrayList<>();

    
}