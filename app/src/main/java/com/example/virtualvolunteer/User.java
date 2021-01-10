package com.example.virtualvolunteer;

public class User {
    private String name;
    private String email;
    private Upload upload;
    private String location;
    private int hours;
    private String bio;
    private String skills;

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
    public void setName(String name){this.name = name;}
    public void setEmail(String email){this.email = email;}
    public void setUpload(Upload upload){this.upload = upload;}
    public void setHours(int hours){this.hours = hours;}
    public void setLocation(String location){this.location = location;}
    public void setSkills(String skills){this.skills = skills;}
    public void setBio(String bio){this.bio = bio;}

}
