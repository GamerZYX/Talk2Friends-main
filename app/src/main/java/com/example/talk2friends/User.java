package com.example.talk2friends;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Vector;

public class User {
    private String username;
    private String name;
    private String email;
    private String region;
    private String password;
    private ArrayList<String> friendList;
    private Vector<Meeting> meetingList;
    private ArrayList<String> interestList;

    // Constructor
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String name, String email, String region, String password, ArrayList<String> interestList) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.region = region;
        this.password = password;
        this.friendList = new ArrayList<>();
        this.meetingList = null;
        this.interestList = interestList;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<String> friendList) {
        this.friendList = friendList;
    }

    public Vector<Meeting> getMeetingList() {
        return meetingList;
    }

    public void setMeetingList(Vector<Meeting> meetingList) {
        this.meetingList = meetingList;
    }

    public ArrayList<String> getInterestList() {
        return interestList;
    }

    public void setInterestList(ArrayList<String> interestList) {
        this.interestList = interestList;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @NonNull
    @Override
    public String toString() {
        return "Username: " + username + '\n' +
                "Name: " + name + '\n' +
                "Email: " + email + '\n' +
                "Region: " + region + '\n' +
                "Interests: " + interestList.toString();
    }
}

