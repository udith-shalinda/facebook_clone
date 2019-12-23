package com.udith.authentication_service.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User{
    @Id
    private ObjectId id;
    private String email;
    private String password;
    private String name;
    private String status;

}