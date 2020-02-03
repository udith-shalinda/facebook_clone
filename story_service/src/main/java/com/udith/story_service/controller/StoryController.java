package com.udith.story_service.controller;

import com.udith.story_service.model.Story;
import com.udith.story_service.repository.StoryRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




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
            System.out.println(e);
        }
        if(s!=null){
            return "done";
        }else{
            return "failed";
        }
    }
    @PostMapping(value="/remove/{storyId}")
    public String getMethodName(@PathVariable("storyId")String storyId) {
        Story story = this.storyRepository.findById(new ObjectId(storyId));
        try {
            this.storyRepository.deleteById(storyId);
            String res = restTemplate.postForObject(
                    "http://user-service/api/story/remove/" + storyId + "/" + story.getUserId(), null,
                    String.class);
            return story.getId().toString() + res;
        } catch (Exception e) {
            System.out.println(e);
            return "failed";
        }
    }
    
    @GetMapping(value="/get/{page}/{count}")
    public String getStories(@PathVariable("page")int page,@PathVariable("count")int count) {
        
        return "new SomeData();";
    }
    

    
}