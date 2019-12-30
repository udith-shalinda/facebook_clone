package com.udith.authentication_service.controller;

import com.udith.authentication_service.model.FriendRequest;
import com.udith.authentication_service.model.User;
import com.udith.authentication_service.repository.UserRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/friendRequest")
public class FriendRequestController{

    @Autowired
    private UserRepository userRepository;
    // @Autowired
    // GraphQLService graphQLServie;


    @PutMapping("/send")
    public String sendFriedRequest(@RequestBody FriendRequest friendRequest){
        try {
            User me = this.userRepository.findById(new ObjectId(friendRequest.getMyId()));
            User friend = this.userRepository.findById(new ObjectId(friendRequest.getFriendId()));
            me.addSendFriendRequest(friendRequest.getFriendId());
            friend.addRecievedFriendRequest(friendRequest.getMyId());
            this.userRepository.save(me);
            this.userRepository.save(friend);
            return "friend request sent successfully";
        } catch (Exception e) {
            return "user not found";
        }
    }

    @PutMapping("/accept")
    public String acceptFriendRequest(@RequestBody FriendRequest friendRequest){
        try {
            User me = this.userRepository.findById(new ObjectId(friendRequest.getMyId()));
            User friend = this.userRepository.findById(new ObjectId(friendRequest.getFriendId()));
            me.removeRecievedFriendRequest(friendRequest.getFriendId());
            friend.removeSendFriendRequest(friendRequest.getMyId());
            me.addFriend(friendRequest.getFriendId());
            friend.addFriend(friendRequest.getMyId());
            this.userRepository.save(me);
            this.userRepository.save(friend);
            return "friend request accepted successfully";
        } catch (Exception e) {
            return "user not found";
        }
    }
}