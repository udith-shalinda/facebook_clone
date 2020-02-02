package com.udith.post_adding_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

import com.udith.post_adding_service.model.Comment;
import com.udith.post_adding_service.model.LikeModel;
import com.udith.post_adding_service.model.Post;
import com.udith.post_adding_service.model.PostResponse;
import com.udith.post_adding_service.model.PostResponseList;
import com.udith.post_adding_service.model.User;
import com.udith.post_adding_service.model.UserResponse;
import com.udith.post_adding_service.repository.PostRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/post")
public class PostAddingController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/add")
    public String addPost(@RequestBody Post post) {
        post.setCommentsId((new ObjectId()).toString());
        Post resPost = this.postRepository.save(post);
        try {
            String res = restTemplate.postForObject(
                    "http://user-service/api/post/add/" + post.getId().toString() + "/" + post.getUserId(), null,
                    String.class);
            return resPost.getId().toString() + res;
        } catch (Exception e) {
            // TODO: handle exception
            return resPost.getId().toString();
        }
    }

    @PostMapping("/addComment/{postId}")
    public String addComment(@PathVariable("postId") String postId, @RequestBody Comment comment) {
        try {
            Post post = this.postRepository.findById(new ObjectId(postId));
            if (post.getCommentsId() != null) {
                String commentListId = restTemplate.postForObject(
                        "http://comments-adding/api/comment/add/" + post.getCommentsId().toString(), comment,
                        String.class);
                return commentListId;
            } else {
                String commentId = restTemplate.postForObject("http://comments-adding/api/comment/add/firstComment",
                        comment, String.class);
                post.setCommentsId(commentId);
                this.postRepository.save(post);
                return post.getId().toString();
            }
        } catch (Exception e) {
            return "post not found";
        }
    }

    @PostMapping("/updatePost/{postId}")
    public String updatePost(@PathVariable("postId")String postId,@RequestBody Post post,@RequestHeader("Authorization") String token) {
        //verify token
        System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<String> verifyToken = restTemplate.exchange(
            "http://user-service/api/user/validateUser",
            HttpMethod.GET,
            request,
            String.class);
        
        if(verifyToken.getStatusCode().value()!=200){
            return "not Autharized";
        }



        Post oldPost = this.postRepository.findById(new ObjectId(postId));
        if(oldPost.getUserId().equals(post.getUserId())){
            oldPost.setId(new ObjectId(postId));
            oldPost.setTitle(post.getTitle());
            oldPost.setSubTitle(post.getSubTitle());
            oldPost.setImageLinks(post.getImageLinks());
            this.postRepository.save(oldPost);
            return "done";
        }else{
            return "user is not equal";
        }
    }

    @PostMapping("/addLike/{postId}/{userId}")
    public String addLike(@PathVariable("postId") String postId, @PathVariable("userId") String userId) {
        try {
            Post post = this.postRepository.findById(new ObjectId(postId));
            if (post.addLike(userId)) {
                this.postRepository.save(post);
                return "successfully liked";
            } else {
                return "user is already liked";
            }

        } catch (Exception e) {
            return "post not found";
        }
    }

    @PostMapping("/reshare")
    public String resharePost(@RequestBody LikeModel likeModel) {
        try {
            Post post = this.postRepository.findById(new ObjectId(likeModel.getPostId()));
            Post newPost = new Post();
            newPost.setReshareId(post.getId().toString());
            // have to add public or only friends;
            Post resPost = this.postRepository.save(newPost);
            String res = restTemplate.postForObject(
                    "http://user-service/api/post/add/" + resPost.getId().toString() + "/" + post.getUserId(), null,
                    String.class);
            return resPost.getId().toString() + res;
        } catch (Exception e) {
            return "post not found";
        }

    }

    @PostMapping("/get/{page}/{count}")
    public PostResponseList getPosts(@PathVariable("page") int page, @PathVariable("count") int count,
            @RequestBody LikeModel user, @RequestHeader("Authorization") String token) {

        System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<String> verifyToken = restTemplate.exchange(
            "http://user-service/api/user/validateUser",
            HttpMethod.GET,
            request,
            String.class);
        
        if(verifyToken.getStatusCode().value()!=200){
            return null;
        }


        Page<Post> post = this.postRepository.findAll(PageRequest.of(page,count));
        return new PostResponseList(post.getContent().stream().map(p->{
            PostResponse postResponse = new PostResponse(p);
            if(user.getUserId().equals(p.getUserId())){
                postResponse.setOwner(true);
            }
            if(p.getLikeList().contains(user.getUserId())){
                postResponse.setLiked(true);
            }
            User res = restTemplate.getForObject("http://user-service/api/user/oneUser/"+user.getUserId(), User.class);
            postResponse.setUserDetails(new UserResponse(res));
            
            
            return postResponse;
        }).collect(Collectors.toList()));
    }
    

    
}
