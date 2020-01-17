package com.udith.post_adding_service.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Post{
    @Id
    private ObjectId id;
    private String userId;
    private String title;
    private String subTitle;
    private List<String> imageLinks = new ArrayList<>();
    private List<String> likeList = new ArrayList<>();
    private String commentsId;
    //to identify whether this post is reshared one or not and have post id in resharedId if reshared;
    private String reshareId;
    //1=public
    //2=only friends
    //3=only me
    private int publicStatus;


    public void addLike(String userId){
        if(likeList != null || likeList.size()==0){
            List<String> list = new ArrayList<>();
            list.add(userId);
            this.likeList = list;
        }else{
            if(!this.likeList.contains(userId)){
                this.likeList.add(userId);
            }
        }
    }
}