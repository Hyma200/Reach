package com.example.virtualvolunteer.HomePage;

import java.util.ArrayList;

public class Post {

    // POJO for post data

    private String email = "";
    private String description;
    private String imageURL;
    private Long creationTime;
    private ArrayList<String> tags;

    public Post() {
        this.email = "";
        this.description = "";
        this.imageURL = "";
        this.creationTime = 0L;
    }

    public Post(String email, String description, String imageURL, Long creationTime, ArrayList<String> tags) {
        this.email = email;
        this.description = description;
        this.imageURL = imageURL;
        this.creationTime = creationTime;
        this.tags = tags;
    }

    public String getEmail() {
        return this.email;
    }

    public String getDescription() {
        return this.description;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public Long getCreationTime() {
        return this.creationTime;
    }

    public ArrayList<String> getTags(){return this.tags;}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
