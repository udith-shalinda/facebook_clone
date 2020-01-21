package com.udith.comment_adding_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.udith.comment_adding_service.model.Comment;
import com.udith.comment_adding_service.model.CommentList;
import com.udith.comment_adding_service.model.CommentListRes;
import com.udith.comment_adding_service.model.CommentRes;
import com.udith.comment_adding_service.model.User;
import com.udith.comment_adding_service.model.UserResponse;
import com.udith.comment_adding_service.repository.CommentAddingRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RequestMapping("/comment")
@RestController
public class CommentAddingController{

    @Autowired
    private CommentAddingRepository commentAddingRepository;
    @Autowired
    private RestTemplate restTemplate; 

    @PostMapping("/add/{CommentListId}")
    public String postMethodName(@PathVariable("CommentListId")String CommentListId ,@RequestBody Comment userComment) {
        System.out.println(CommentListId);
        ObjectId objectId = new ObjectId();
        userComment.setId(objectId);
        CommentList commentList;
        try {
            commentList = this.commentAddingRepository.findById(new ObjectId(CommentListId));
            commentList.addComment(userComment);
            this.commentAddingRepository.save(commentList);
            return commentList.getId().toString();
        } catch (Exception e) {
            System.out.println(e);
            commentList = new CommentList();
            commentList.setId(new ObjectId(CommentListId));

            List<Comment> comments = new ArrayList<>();
            comments.add(userComment);
            commentList.setCommments(comments);
            CommentList comm = this.commentAddingRepository.save(commentList);
            return comm.getId().toString(); 
        }
        
    }

    @GetMapping("getComments/{commentListId}/{userId}")
    public CommentListRes getMethodName(@PathVariable("commentListId") String commentListId,@PathVariable("userId")String userId) {

        CommentList commentList = this.commentAddingRepository.findById(new ObjectId(commentListId));
        
         return new CommentListRes(commentList.getCommments().stream().map(comment->{
            User res = restTemplate.getForObject("http://user-service/api/user/oneUser/"+comment.getUserId(), User.class);
            CommentRes commentRes = new CommentRes(comment);
            commentRes.setUser(new UserResponse(res));
            if(userId.equals(comment.getUserId())){
                commentRes.setOwner(true);
            }
            return commentRes;
         }).collect(Collectors.toList()));
    }
    
    
}