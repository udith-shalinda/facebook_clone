package com.udith.post_adding_service.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import lombok.Data;

@Data
public class User{
    private ObjectId id;
    private String email;
    private String password;
    private String name;
    private String status;
    private String profileImage;
    private List<String> friends=new ArrayList<>();
    private List<String> sentFriendRequest = new ArrayList<>();
    private List<String> RecievedFriendRequests = new ArrayList<>();
    private List<String> postsIdList = new ArrayList<>();


}


// class Animal{
//     public String name;
//     public int age;

//     public void eat(){
//         //
//     }
// }

// class test{

//     public void name() {
//         Animal dog = new Animal();
//         dog.name = "name";
//         dog.age = 12;
//         dog.eat();
//     }
// }

