package com.udith.story_service.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import lombok.Data;

@Data
public class Story{
    private ObjectId id;
    private String url;
    private String userId;
    private List<String> viewsList=new ArrayList<>();
    
}