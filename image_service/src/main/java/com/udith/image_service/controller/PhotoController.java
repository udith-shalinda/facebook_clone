package com.udith.image_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

import com.udith.image_service.model.Photo;
import com.udith.image_service.services.PhotoService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    public PhotoService photoService;

    @PostMapping("/add")
    public String addPhoto(@RequestParam("title") String title,
                           @RequestParam("image") MultipartFile image, Model model)
            throws IOException {
        String id = photoService.addPhoto(title, image);
        return id;
    }

    @GetMapping("/getPhoto/{id}")
    public String getPhoto(@PathVariable String id, Model model) {
        Photo photo = photoService.getPhoto(id);
        model.addAttribute("title", photo.getTitle());
        model.addAttribute("image",
                Base64.getEncoder().encodeToString(photo.getImage().getData()));
        return Base64.getEncoder().encodeToString(photo.getImage().getData());
    }
}
