package com.example.virtualvolunteer;

import java.util.ArrayList;

public class User {

    // POJO for User data

    private String name;
    private String email;
    private Upload upload;
    private String location;
    private int hours;
    private String bio;
    private String skills;
    private int validHours;

    private static ArrayList<Long> savedPosts = new ArrayList<>();
    private static ArrayList<String> orgs = new ArrayList<>();

    public User() {
        this.name = "";
        this.email = "";
        this.upload = null;
        this.location = "";
        this.hours = 0;
        this.bio = "";
        this.skills = "";
        this.validHours = 0;
    }

    public User(String name, String email, Upload upload, String location, int hours) {
        this.name = name;
        this.email = email;
        this.upload = upload;
        this.location = location;
        this.hours = hours;
    }

    public User(String name, String email, Upload upload, String location, int hours, String bio, String skills) {
        this.name = name;
        this.email = email;
        this.upload = upload;
        this.location = location;
        this.hours = hours;
        this.bio = bio;
        this.skills = skills;
    }

    public User(String name, String email, Upload upload, String location, int hours, String bio, String skills, ArrayList<Long> posts, ArrayList<String> orgs, int validHours) {
        this.name = name;
        this.email = email;
        this.upload = upload;
        this.location = location;
        this.hours = hours;
        this.bio = bio;
        this.validHours = validHours;
        this.skills = skills;
        this.savedPosts = new ArrayList<Long>();
        this.orgs = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Upload getUpload() {
        return upload;
    }

    public String getLocation() {
        return location;
    }

    public int getHours() {
        return hours;
    }

    public int getValidHours() {
        return validHours;
    }

    public String getSkills() {
        return skills;
    }

    public String getBio() {
        return bio;
    }

    public ArrayList<Long> getPosts() {
        return savedPosts;
    }

    public ArrayList<String> getOrgs() {
        return orgs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUpload(Upload upload) {
        this.upload = upload;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setValidHours(int hours) {
        this.validHours = hours;
    }

    public void addOrg(String org) {
        this.orgs.add(org);
    }

    public String addPost(long post) {
        if (savedPosts.contains(post)) {
            savedPosts.remove(post);
            return "Post Unsaved";
        } else {
            savedPosts.add(post);
            return "Post Saved";
        }
    }
}
