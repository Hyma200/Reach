package com.example.virtualvolunteer;

public class Post {
    // POJO for post data

    private static final long serialVersionUID = -3760445487636086034L;
    private String userName;
    private String description;
    private String imageURL;
    private Long creationTime;

    public Post() {
        this.userName = null;
        this.description = "";
        this.imageURL = "";
        this.creationTime = 0L;
    }

    public Post(String userName, String description, String imageURL, Long creationTime) {
        this.userName = userName;
        this.description = description;
        this.imageURL = imageURL;
        this.creationTime = creationTime;
    }

    public String getUser() {
        return this.userName;
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

    public void setUser(String user) {
        this.userName = user;
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
