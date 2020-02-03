package com.udith.authentication_service.controller;

import com.udith.authentication_service.model.User;
import com.udith.authentication_service.repository.UserRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/story")
public class StoryHandleController{
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/add/{storyId}/{userId}")
    public String addPostToUser(@PathVariable("storyId") String storyId,@PathVariable("userId") String userId){
        try {
            User me = this.userRepository.findById(new ObjectId(userId));
            me.addStoryToList(storyId);
            this.userRepository.save(me);
            return "post added successfully";
        } catch (Exception e) {
            return "user is not found";
        }
    }

    @PostMapping("/remove/{storyId}/{userId}")
    public String removePostFromUser(@PathVariable("storyId") String storyId,@PathVariable("userId") String userId){
        try {
            User me = this.userRepository.findById(new ObjectId(userId));
            me.removeStoryId(storyId);
            this.userRepository.save(me);
            return "story removed successfully";
        } catch (Exception e) {
            return "user is not found";
        }
    }
}