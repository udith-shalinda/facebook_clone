package com.udith.post_adding_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.udith.post_adding_service.model.Comment;
import com.udith.post_adding_service.model.LikeModel;
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
        String res = restTemplate.postForObject("http://user-service/api/post/add/"+post.getId().toString()+"/"+post.getUserId(),null,String.class);
        return resPost.getId().toString()+res;
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

    @PostMapping("/addLike")
    public String addLike(@RequestBody LikeModel likeModel){
        try {
            Post post  = this.postRepository.findById(new ObjectId(likeModel.getPostId()));
            post.addLike(likeModel.getUserId());
            return "successfully liked";
        } catch (Exception e) {
            return "post not found";
        }
    }

    @PostMapping("/reshare")
    public String postMethodName(@RequestBody LikeModel likeModel) {
        try {
            Post post  = this.postRepository.findById(new ObjectId(likeModel.getPostId()));
            post.setReshareId(post.getId().toString());
            post.setId(new ObjectId());
            Post resPost = this.postRepository.save(post);
            String res = restTemplate.postForObject("http://user-service/api/post/add/"+resPost.getId().toString()+"/"+post.getUserId(),null,String.class);
            return resPost.getId().toString()+res;
        } catch (Exception e) {
            return "post not found";
        }
        
    }
    

    
}
