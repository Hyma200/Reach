package com.example.virtualvolunteer;

public class User {
    public String name;
    public String email;
    public Upload upload;
    public String location;
    public int hours;

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

    public void setName(String name){this.name = name;}
    public void setEmail(String email){this.email = email;}
    public void setUpload(Upload upload){this.upload = upload;}
    public void setHours(int hours){this.hours = hours;}
    public void setLocation(String location){this.location = location;}

}
