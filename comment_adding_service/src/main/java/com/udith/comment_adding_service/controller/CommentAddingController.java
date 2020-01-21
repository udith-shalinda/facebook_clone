package com.udith.comment_adding_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import com.udith.comment_adding_service.model.Comment;
import com.udith.comment_adding_service.model.CommentList;
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

    @GetMapping("getComments/{commentListId}")
    public CommentList getMethodName(@PathVariable("commentListId") String commentListId) {
        return this.commentAddingRepository.findById(new ObjectId(commentListId));
    }
    
    
}