package com.udith.image_service.model;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "photos")
public class Photo {
    @Id
    private String id;

    private String title;

    private Binary image;

    public Photo(String title){
        this.title = title;
    }

    public void setImage(Binary binary) {
        this.image = binary;
    }
    public String getId(){
        return this.id;
    }
    public String getTitle(){
        return this.title;
    }
    public Binary getImage(){
        return this.image;
    }
}