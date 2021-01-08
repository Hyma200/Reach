package com.example.virtualvolunteer;

public class User {
    private String name;
    private String email;
    private Upload upload;
    private String location;
    private int hours;

    public User (){}
    public User (String name, String email, Upload upload, String location, int hours){
        this.name = name;
        this.email = email;
        this.upload = upload;
        this.location = location;
        this.hours = hours;
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

    public void setUpload(Upload upload){
        this.upload = upload;
    }
}
