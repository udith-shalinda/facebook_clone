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
    private String profileImage;
    private List<String> friends=new ArrayList<>();
    private List<String> sentFriendRequest = new ArrayList<>();
    private List<String> RecievedFriendRequests = new ArrayList<>();
    private List<String> postsIdList = new ArrayList<>();

    public void addFriend(String friendId){
        if(friends== null ||friends.size()==0){
            List<String> list = new ArrayList<>();
            list.add(friendId);
            this.friends = list;
        }else{
            this.friends.add(friendId);
        }
    }
    public void addSendFriendRequest(String friendId){
        if(sentFriendRequest== null ||sentFriendRequest.size()==0){
            List<String> list = new ArrayList<>();
            list.add(friendId);
            this.sentFriendRequest = list;
        }else{
            this.sentFriendRequest.add(friendId);
        }
    }
    public void addRecievedFriendRequest(String friendId){
        if(RecievedFriendRequests== null ||RecievedFriendRequests.size()==0){
            List<String> list = new ArrayList<>();
            list.add(friendId);
            this.RecievedFriendRequests = list;
        }else{
            this.RecievedFriendRequests.add(friendId);
        }
    }
    public void addPostToList(String postId){
        if(postsIdList== null ||postsIdList.size()==0){
            List<String> list = new ArrayList<>();
            list.add(postId);
            this.postsIdList = list;
        }else{
            this.postsIdList.add(postId);
        }
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
    public void removePostId(String postId){
        this.postsIdList.remove(postId);
    }
}