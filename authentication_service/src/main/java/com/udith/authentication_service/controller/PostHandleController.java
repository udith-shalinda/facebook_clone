package com.udith.authentication_service.controller;

import com.udith.authentication_service.model.User;
import com.udith.authentication_service.repository.UserRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/post")
public class PostHandleController{

    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/add/{postId}/{userId}")
    public String addPostToUser(@PathVariable("postId") String postId,@PathVariable("userId") String userId){
        try {
            User me = this.userRepository.findById(new ObjectId(userId));
            me.addPostToList(postId);
            this.userRepository.save(me);
            return "post added successfully";
        } catch (Exception e) {
            return "user is not found";
        }
    }

    @GetMapping("/remove/{postId}/{userId}")
    public String removePostFromUser(@PathVariable("postId") String postId,@PathVariable("userId") String userId){
        try {
            User me = this.userRepository.findById(new ObjectId(userId));
            me.removePostId(postId);
            this.userRepository.save(me);
            return "post removed successfully";
        } catch (Exception e) {
            return "user is not found";
        }
    }

}