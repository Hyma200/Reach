package com.example.virtualvolunteer.LoggingPage;

public class Hour {
    private String org;
    private int hours;
    private String event;
    private String email;
    private String date;
    private String verify;
    private boolean verified;

    public Hour() {
        this.org = "";
        this.hours = 0;
        this.event = "";
        this.email = "";
        this.date = "";
        this.verify = "";
        this.verified = false;
    }

    public Hour(String org, int hours, String event, String email, String date, String verify, boolean verified) {
        this.org = org;
        this.hours = hours;
        this.event = event;
        this.email = email;
        this.date = date;
        this.verified = verified;
        this.verify = verify;
    }

    public String getOrg() {
        return org;
    }

    public int getHours() {
        return hours;
    }

    public String getEvent() {
        return event;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }

    public String getVerify() {
        return verify;
    }

    public boolean getVerified() {
        return verified;
    }

    public void setVerification(String verify) {
        this.verify = verify;
    }

    public void setVerified() {
        this.verified = true;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
