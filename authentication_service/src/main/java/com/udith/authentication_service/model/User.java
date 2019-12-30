package com.udith.authentication_service.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User{
    @Id
    private ObjectId id;
    private String email;
    private String password;
    private String name;
    private String status;
    private List<String> friends=new ArrayList<>();
    private List<String> sentFriendRequest = new ArrayList<>();
    private List<String> RecievedFriendRequests = new ArrayList<>();

    public void addFriend(String friendId){
        this.friends.add(friendId);
    }
    public void addSendFriendRequest(String friendId){
        this.sentFriendRequest.add(friendId);
    }
    public void addRecievedFriendRequest(String friendId){
        this.RecievedFriendRequests.add(friendId);
    }

    public void removeFriend(String friendId){
        this.friends.remove(friendId);
    }
    public void removeSendFriendRequest(String friendId){
        this.sentFriendRequest.remove(friendId);
    }
    public void removeRecievedFriendRequest(String friendId){
        this.RecievedFriendRequests.remove(friendId);
    }
}