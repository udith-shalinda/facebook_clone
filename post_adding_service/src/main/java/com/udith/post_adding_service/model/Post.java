package com.udith.post_adding_service.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Post{
    @Id
    private ObjectId id;
    private String userId;
    private String title;
    private String subTitle;
    private List<String> imageLinks = new ArrayList<>();
    private String commentsId;
}