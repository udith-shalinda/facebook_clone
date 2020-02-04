package com.udith.authentication_service.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UserResponse{
    private String id;
    private String name;
    private String status;
    private String profileImage;
    private List<String> friends=new ArrayList<>();
    private List<String> sentFriendRequest = new ArrayList<>();
    private List<String> RecievedFriendRequests = new ArrayList<>();
    private List<String> postsIdList = new ArrayList<>();
    private List<String> storyIdList = new ArrayList<>();
    private boolean myFirend;


    public UserResponse(User user){
        this.id = user.getId().toString();
        this.name = user.getName();
        this.status = user.getStatus();
        this.profileImage = user.getProfileImage();
        this.friends = user.getFriends();
        this.sentFriendRequest = user.getSentFriendRequest();
        this.RecievedFriendRequests = user.getRecievedFriendRequests();
        this.postsIdList = user.getPostsIdList();
        this.storyIdList = user.getStoryIdList();
    }
}