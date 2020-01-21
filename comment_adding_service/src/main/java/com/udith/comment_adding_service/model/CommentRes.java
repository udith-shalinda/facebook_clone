package com.udith.comment_adding_service.model;

import lombok.Data;

@Data
public class CommentRes{
    private String id;
    private UserResponse user;
    private String commentContent;
    private boolean owner = false;

    public CommentRes(Comment comment){
        this.id = comment.getId().toString();
        this.commentContent = comment.getCommantContent();
    }
    
}