package com.udith.comment_adding_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udith.comment_adding_service.model.Comment;
import com.udith.comment_adding_service.repository.CommentAddingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/comment")
@RestController
public class CommentAddingController{

    @Autowired
    private CommentAddingRepository commentAddingRepository;

    @PostMapping("/add")
    public String postMethodName(@RequestBody Comment userComment) {
        Comment com = commentAddingRepository.save(userComment);
        return com.getId().toString();
    }
    
}