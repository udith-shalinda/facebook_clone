package com.udith.post_adding_service.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Post{
    private String title;
    private String subTitle;
    private List<String> imageLinks = new ArrayList<>();
    private List<String> comments = new ArrayList<>();
}