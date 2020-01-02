package com.udith.post_adding_service.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PostResponse{
    private String id;
    private String title;
    private String subTitle;
    private List<String> imageLinkList = new ArrayList<>();
    private String userId;
    private boolean owner;
    private boolean liked;
    private int likeCount;
    private int shareCount;
    private String userDetails;
    private List<Comment> commentList;
    private int CommentCount;

    public PostResponse(String title,String subTitle,String userId){
        this.title=title;
        this.subTitle=subTitle;
        this.userId=userId;
    }

}