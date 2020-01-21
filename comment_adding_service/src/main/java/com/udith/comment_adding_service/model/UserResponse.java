package com.udith.comment_adding_service.model;

import lombok.Data;

@Data
public class UserResponse{
    private String id;
    private String name;
    private String status;
    private String profileImage;

    public UserResponse(User user){
        this.id = user.getId().toString();
        this.name = user.getName();
        this.status = user.getStatus();
        this.profileImage = user.getProfileImage();
    }
}