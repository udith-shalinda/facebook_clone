package com.udith.authentication_service.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.udith.authentication_service.model.FriendRequest;
import com.udith.authentication_service.model.FriendRequestsResponse;
import com.udith.authentication_service.model.User;
import com.udith.authentication_service.model.UserResponse;
import com.udith.authentication_service.repository.UserRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



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
            me.setName("pubudu sandeepa");
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
    @GetMapping(value="/delete")
    public String getMethodName(@RequestParam String param) {
        return "new SomeData();";
    }

    @GetMapping(value="/getRequests/{userId}")
    public FriendRequestsResponse getFriendRequests(@PathVariable("userId")String userId) {
        User me =  this.userRepository.findById(new ObjectId(userId));
        return new FriendRequestsResponse(me.getRecievedFriendRequests().stream().map((friendId)->{
            User friend = this.userRepository.findById(new ObjectId(friendId));
            return new UserResponse(friend);
        }).collect(Collectors.toList())
        );
    }

    @GetMapping(value="/getSuggesions/{page}/{count}/{userId}")
    public FriendRequestsResponse getFriendSuggesions(@PathVariable("page") int page, @PathVariable("count") int count,
                                                            @PathVariable("userId")String userId) {
        User me =  this.userRepository.findById(new ObjectId(userId)); 
        Page<User> userList = this.userRepository.findAll(PageRequest.of(page,count));
        
        List<UserResponse> users = new ArrayList<>();
        // try {
            for(User user : userList.getContent()){
                if(!userId.equals(user.getId().toString()) && (me.getFriends()==null || !me.getFriends().contains(user.getId().toString()))){
                    users.add(new UserResponse(this.userRepository.findById(user.getId()))); 
                }
            } 
            return new FriendRequestsResponse(users);
        // } catch (Exception e) {
        //     return "";
        // }                                                       
    }
    

    

    
}