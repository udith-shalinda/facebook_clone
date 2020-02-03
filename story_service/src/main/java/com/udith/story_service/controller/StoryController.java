package com.udith.story_service.controller;

import com.udith.story_service.model.Story;
import com.udith.story_service.repository.StoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/story")
public class StoryController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private StoryRepository storyRepository;
    
    @PostMapping(value="/add")
    public String addStory(@RequestBody Story story) {
        Story s = this.storyRepository.save(story);
        try {
            String res = restTemplate.postForObject(
                    "http://user-service/api/story/add/" + story.getId().toString() + "/" + story.getUserId(), null,
                    String.class);
            return s.getId().toString() + res;
        } catch (Exception e) {
            //TODO: handle exception
        }
        if(s!=null){
            return "done";
        }else{
            return "failed";
        }
    }
    
}