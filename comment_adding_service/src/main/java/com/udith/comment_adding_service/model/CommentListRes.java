package com.udith.comment_adding_service.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentListRes{

    private List<CommentRes> commentlist = new ArrayList<>();

    public void addComment(CommentRes commentRes){
        this.commentlist.add(commentRes);
    }
}