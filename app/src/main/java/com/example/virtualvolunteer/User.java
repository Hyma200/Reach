package com.example.virtualvolunteer;

import com.example.virtualvolunteer.HomePage.Post;

import java.util.ArrayList;

public class User {
    private String name;
    private String email;
    private Upload upload;
    private String location;
    private int hours;
    private String bio;
    private String skills;
    private static ArrayList<Long> posts = new ArrayList<>();

    public User (){}
    public User (String name, String email, Upload upload, String location, int hours){
        this.name = name;
        this.email = email;
        this.upload = upload;
        this.location = location;
        this.hours = hours;
    }

    public User (String name, String email, Upload upload, String location, int hours, String bio, String skills){
        this.name = name;
        this.email = email;
        this.upload = upload;
        this.location = location;
        this.hours = hours;
        this.bio = bio;
        this.skills = skills;
    }

    public User (String name, String email, Upload upload, String location, int hours, String bio, String skills, ArrayList<Long> posts){
        this.name = name;
        this.email = email;
        this.upload = upload;
        this.location = location;
        this.hours = hours;
        this.bio = bio;
        this.skills = skills;
        this.posts = new ArrayList<Long>();
    }

    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }

    public Upload getUpload(){
        return upload;
    }
    public String getLocation(){
        return location;
    }
    public int getHours(){
        return hours;
    }
    public String getSkills(){return skills;}
    public String getBio(){return bio;}
    public ArrayList<Long> getPosts(){return posts;}
    public String addPost(long post){
        if (posts.contains(post)){
            posts.remove(post);
            return "Post Deleted";
        }
        else {
            posts.add(post);
            return "Post Saved";
        }
    }
    public void setName(String name){this.name = name;}
    public void setEmail(String email){this.email = email;}
    public void setUpload(Upload upload){this.upload = upload;}
    public void setHours(int hours){this.hours = hours;}
    public void setLocation(String location){this.location = location;}
    public void setSkills(String skills){this.skills = skills;}
    public void setBio(String bio){this.bio = bio;}

}
