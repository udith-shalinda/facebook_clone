package com.udith.comment_adding_service.model;

import java.util.ArrayList;
import java.util.List;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class CommentList{

    @Id
    private ObjectId id;
    private List<Comment> commments = new ArrayList<>();

    public void addComment(Comment comment){
        this.commments.add(comment);
    }
}