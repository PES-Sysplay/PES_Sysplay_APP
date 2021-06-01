package com.example.workoutapp;

public class Review {

    int id;
    float stars;
    String comment;
    String username;

    public Review() {
    }

    public Review(int id, float stars, String comment, String username) {
        this.id = id;
        this.stars = stars;
        this.comment = comment;
        this.username = username;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRating() {
        return stars;
    }

    public String getComment() {
        return comment;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
