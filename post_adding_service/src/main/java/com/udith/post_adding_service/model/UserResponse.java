package com.udith.post_adding_service.model;

import lombok.Data;

@Data
public class UserResponse{
    private String id;
    private String name;
    private String status;
    private String profileImage;
    private boolean myFriend;

    public UserResponse(User user){
        this.id = user.getId().toString();
        this.name = user.getName();
        this.status = user.getStatus();
        this.profileImage = user.getProfileImage();
        // this.myFriend = 
    }
}