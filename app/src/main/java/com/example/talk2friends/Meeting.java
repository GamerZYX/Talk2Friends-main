package com.example.talk2friends;

import androidx.annotation.NonNull;

public class Meeting {
    private String name;
    private String topic;
    private String time;
    private String region;
    private String location;

    private String id;

    // Constructor
    public Meeting() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Meeting(String name, String topic, String time, String region, String location) {
        this.name = name;
        this.topic = topic;
        this.time = time;
        this.region = region;
        this.location = location;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    // Optional: Override the toString() method to print the user's information
    @NonNull
    @Override
    public String toString() {
        return "Name: " + name + '\n' +
                "Topic: " + topic + '\n' +
                "Time: " + time + '\n' +
                "Region: " + region + '\n' +
                "Location: " + location + '\n';
    }
}

