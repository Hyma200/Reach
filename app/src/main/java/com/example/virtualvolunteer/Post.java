package com.example.virtualvolunteer;

public class Post {
    // POJO for post data

    private static final long serialVersionUID = -3760445487636086034L;
    private String email = "";
    private String description;
    private String imageURL;
    private Long creationTime;

    public Post() {
        this.email = "";
        this.description = "";
        this.imageURL = "";
        this.creationTime = 0L;
    }

    public Post(String email, String description, String imageURL, Long creationTime) {
        this.email = email;
        this.description = description;
        this.imageURL = imageURL;
        this.creationTime = creationTime;
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
