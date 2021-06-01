package com.example.workoutapp;

public class Message {

    String text;

    String username;

    float date_timestamp; //secs

    public Message(String text, String user, Long date_timestamp) {
        this.text = text;
        this.username = user;
        this.date_timestamp = date_timestamp;
    }

    public String getUsername() { return username; }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserId() {
        return username;
    }

    public float getDate_timestamp() { return date_timestamp; }
}
