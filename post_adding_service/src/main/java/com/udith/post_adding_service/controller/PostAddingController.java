package com.udith.post_adding_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udith.post_adding_service.model.Post;
import com.udith.post_adding_service.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/post")
public class PostAddingController{

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/add")
    public String addPost(@RequestBody Post post) {
        Post resPost = this.postRepository.save(post);
        return resPost.getId().toString();
    }

    @PostMapping("/addComment")
    public String addComment(@RequestBody String comment ){
        //need to complete;
        return comment;
    }

    @PostMapping("/updatePost")
    public String updatePost(){
        return "sfsfs";
    }

    @PostMapping("/addLike/{userId}")
    public String addLike(@PathVariable("userId")String userId){
        return userId;
    }

    
}
