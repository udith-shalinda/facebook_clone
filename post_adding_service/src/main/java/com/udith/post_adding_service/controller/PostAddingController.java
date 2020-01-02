package com.udith.post_adding_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import com.udith.post_adding_service.model.Comment;
import com.udith.post_adding_service.model.LikeModel;
import com.udith.post_adding_service.model.Post;
import com.udith.post_adding_service.model.PostResponse;
import com.udith.post_adding_service.repository.PostRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;




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
        String res = restTemplate.postForObject("http://user-service/api/post/add/"
            +post.getId().toString()+"/"+post.getUserId(),null,String.class);
        return resPost.getId().toString()+res;
    }

    @PostMapping("/addComment/{postId}")
    public String addComment(@PathVariable("postId")String postId,@RequestBody Comment comment ){
        try{
            Post post = this.postRepository.findById(new ObjectId(postId));
            if(post.getCommentsId() != null){
                String commentListId = restTemplate.postForObject("http://comments-adding/api/comment/add/"
                    +post.getCommentsId().toString(),comment,String.class);
                return commentListId;
            }else{
                String commentId = restTemplate.postForObject("http://comments-adding/api/comment/add/firstComment",
                    comment,String.class);
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
    public String resharePost(@RequestBody LikeModel likeModel) {
        try {
            Post post  = this.postRepository.findById(new ObjectId(likeModel.getPostId()));
            Post newPost = new Post();
            newPost.setReshareId(post.getId().toString());
            //have to add public or only friends;
            Post resPost = this.postRepository.save(newPost);
            String res = restTemplate.postForObject("http://user-service/api/post/add/"
                +resPost.getId().toString()+"/"+post.getUserId(),null,String.class);
            return resPost.getId().toString()+res;
        } catch (Exception e) {
            return "post not found";
        }
        
    }
    
    @PostMapping("/get/{page}/{count}")
    public List<Post> getPosts(@PathVariable("page") int page,@PathVariable("count") int count,@RequestBody LikeModel user) {
        System.out.println(user.getUserId());
        Page<Post> post = this.postRepository.findAll(PageRequest.of(page,count));

        // PostResponse postResponse;
        for(Post inputPost:post.getContent()){
            // postResponse = new PostResponse(inputPost.getTitle(),inputPost.getSubTitle(),inputPost.getUserId());
            try {
                String reqBody= "{"
                    +"user("
                    +"id:'5e00ae40b479aa2d726ac955'"
                    +"){"
                        +"email"
                    +"}"
                +"}";
                ResponseEntity<Object> res = restTemplate.postForObject("http://user-service/api/user/getOneUserDetails",reqBody,ResponseEntity.class);
                System.out.println(res);
            } catch (Exception e) {
                //TODO: handle exception
                System.out.println("exeption");
            }
            
        }

        System.out.println(post.getContent());
        return post.getContent();
    }
    

    
}
