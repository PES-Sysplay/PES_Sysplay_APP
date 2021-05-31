package com.example.workoutapp;

public class Review {
    float stars;
    String comment;
    //String username;
    //String date;

    public Review() {
    }

    public Review(float rating, String comment, String username, String date) {
        this.stars = rating;
        this.comment = comment;
        //this.username = username;
        //this.date = date;
    }

    public void setRating(float rating) {
        this.stars = rating;
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

}
