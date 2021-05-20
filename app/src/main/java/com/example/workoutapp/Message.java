package com.example.workoutapp;

public class Message {

    String text;
    String user_token;

    public Message(String text, String user) {
        this.text = text;
        this.user_token = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserId() {
        return user_token;
    }

    public void setUser(String user) {
        this.user_token = user;
    }
}
