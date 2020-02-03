package com.udith.story_service.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import lombok.Data;

@Data
public class User{
    private ObjectId id;
    private String email;
    private String password;
    private String name;
    private String status;
    private String profileImage;
    private List<String> friends=new ArrayList<>();
    private List<String> sentFriendRequest = new ArrayList<>();
    private List<String> RecievedFriendRequests = new ArrayList<>();
    private List<String> postsIdList = new ArrayList<>();


}