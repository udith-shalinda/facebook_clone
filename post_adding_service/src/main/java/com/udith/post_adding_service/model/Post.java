package com.udith.post_adding_service.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

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
    private String publicStatus;
    //to identify whether this post is reshared one or not and have post id in resharedId if reshared;
    //1=public
    //2=only friends
    //3=only me
    private int reshareId;
}