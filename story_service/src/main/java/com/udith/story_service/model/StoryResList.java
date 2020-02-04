package com.udith.story_service.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoryResList{
    private List<Story> storyList = new ArrayList<Story>();
}