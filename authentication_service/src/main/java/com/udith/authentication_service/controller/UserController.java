package com.udith.authentication_service.controller;

import java.util.ArrayList;

import com.udith.authentication_service.config.JwtTokenUtil;
import com.udith.authentication_service.model.TokenResponse;
import com.udith.authentication_service.model.User;
import com.udith.authentication_service.model.UserResponse;
import com.udith.authentication_service.repository.UserRepository;
import com.udith.authentication_service.service.GraphQLService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import graphql.ExecutionResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/user")
public class UserController{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    GraphQLService graphQLServie;

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        User user1 = this.userRepository.findByEmail(user.getEmail());
        if(user1==null){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            final UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
            final String token = jwtTokenUtil.generateToken(userDetails);
            return new ResponseEntity<>(new TokenResponse(token,user.getId().toString()), HttpStatus.resolve(200));
        }else{
            return new ResponseEntity<>("email is already taken", HttpStatus.resolve(400));
        }
    }

    
    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody User inputUser){
        try {
            User user = this.userRepository.findByEmail(inputUser.getEmail());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if(encoder.matches(inputUser.getPassword(),user.getPassword())){
                final UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
                final String token = jwtTokenUtil.generateToken(userDetails);
                return new ResponseEntity<>(new TokenResponse(token,user.getId().toString()), HttpStatus.resolve(200));
            }else{
                return new ResponseEntity<>("password", HttpStatus.resolve(203));
            }
        } catch (Exception e) {
            return new ResponseEntity<>("email", HttpStatus.resolve(404));
        }

    }

    @GetMapping("/oneUser/{userId}")
    public UserResponse getMethodName(@PathVariable("userId")String userId) {
        try {
            User user = this.userRepository.findById(new ObjectId(userId));
            UserResponse userResponse = new UserResponse(user);
            if(user.getFriends()!=null && user.getFriends().contains(userId)){{
                userResponse.setMyFriend(true);
            }}
            return userResponse;
        } catch (Exception e) {
            System.out.println("error");
            return null;
        }
    }

    @PostMapping("/getOneUserDetails")
    public ResponseEntity<Object> loginUser(@RequestBody String query){
        ExecutionResult execute =  graphQLServie.getGraphQL().execute(query);
        return new ResponseEntity<Object>(execute,HttpStatus.OK);
    }
    @PostMapping("/update/{userId}")
    public String updateUser(@PathVariable("userId")String userId,@RequestBody User user){
        User oldUser = this.userRepository.findById(new ObjectId(userId));
        oldUser.setName(user.getName());
        oldUser.setStatus(user.getStatus());
        oldUser.setProfileImage(user.getProfileImage());
        this.userRepository.save(oldUser);
        return "user updated";
    }

    @GetMapping("/validateUser")
    public String validateUser(@RequestHeader("Authorization") String token) {
        // System.out.println(token);
        return "token";
    }
    
}