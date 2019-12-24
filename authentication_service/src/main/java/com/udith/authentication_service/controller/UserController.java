package com.udith.authentication_service.controller;

import com.udith.authentication_service.model.User;
import com.udith.authentication_service.repository.UserRepository;
import com.udith.authentication_service.service.GraphQLService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import graphql.ExecutionResult;

@RestController
@RequestMapping("/user")
public class UserController{

    @Autowired
    private UserRepository userRepository;
    // @Autowired
    // GraphQLService graphQLServie;

    @PostMapping("/save")
    public void saveUser(@RequestBody User user){
        // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    // @PostMapping("/login")
    // public ResponseEntity<Object> loginUser(@RequestBody String query){
    //     ExecutionResult execute =  graphQLServie.getGraphQL().execute(query);
    //     return new ResponseEntity<Object>(execute,HttpStatus.OK);
    // }
}