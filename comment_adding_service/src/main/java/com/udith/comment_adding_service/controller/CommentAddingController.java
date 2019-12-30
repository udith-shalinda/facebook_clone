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

    @PostMapping("/add/{id}")
    public String postMethodName(@PathVariable("id")String id ,@RequestBody Comment userComment) {
        if("firstComment".equals(id)){
            List<Comment> comments = new ArrayList<>();
            comments.add(userComment);
            CommentList commentList = new CommentList();
            commentList.setCommments(comments);
            CommentList comm = this.commentAddingRepository.save(commentList);
            return comm.getId().toString();
        }else{
            CommentList list = this.commentAddingRepository.findById(new ObjectId(id));
            list.addComment(userComment);
            this.commentAddingRepository.save(list);
            return list.getId().toString();
        }
    }

    @GetMapping("getOneComment/{commentId}")
    public void getMethodName(@PathVariable("commentId") String commentId) {
        // return this.commentAddingRepository.findById(new ObjectId(commentId));
    }
    
    
}