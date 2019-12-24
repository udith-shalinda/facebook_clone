package com.udith.authentication_service.service.data_fetcher;

import com.udith.authentication_service.model.User;
import com.udith.authentication_service.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class FetchUser implements DataFetcher<User>{

    @Autowired
    UserRepository userRepository;

    @Override
    public User get(DataFetchingEnvironment dataFetchingEnvironment){
        String email = dataFetchingEnvironment.getArgument("email");
        return userRepository.findByEmail(email);
    }   

}