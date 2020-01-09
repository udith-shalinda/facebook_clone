package com.udith.post_adding_service.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostResponseList{
    private List<PostResponse> posts = new ArrayList<>();
}