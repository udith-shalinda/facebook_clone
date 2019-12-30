package com.udith.post_adding_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.udith.post_adding_service.model.Comment;
import com.udith.post_adding_service.model.Post;
import com.udith.post_adding_service.repository.PostRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/post")
public class PostAddingController{

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private RestTemplate restTemplate; 

    @PostMapping("/add")
    public String addPost(@RequestBody Post post) {
        Post resPost = this.postRepository.save(post);
        return resPost.getId().toString();
    }

    @PostMapping("/addComment/{postId}")
    public String addComment(@PathVariable("postId")String postId,@RequestBody Comment comment ){
        try{
            Post post = this.postRepository.findById(new ObjectId(postId));
            if(post.getCommentsId() != null){
                String commentListId = restTemplate.postForObject("http://comments-adding/api/comment/add/"+post.getCommentsId().toString(),comment,String.class);
                return commentListId;
            }else{
                String commentId = restTemplate.postForObject("http://comments-adding/api/comment/add/firstComment",comment,String.class);
                post.setCommentsId(commentId);
                this.postRepository.save(post);
                return post.getId().toString();
            }
        }catch(Exception e){
            return "post not found";
        }
    }

    @PostMapping("/updatePost")
    public String updatePost(){
        //have to complete
        return "sfsfs";
    }

    @PostMapping("/addLike/{userId}")
    public String addLike(@PathVariable("userId")String userId){
        return userId;
    }

    
}
