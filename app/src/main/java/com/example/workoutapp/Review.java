package com.example.workoutapp;

public class Review {
    float rating;
    String comment;
    //String username;
    //String date;

    public Review() {
    }

    public Review(float rating, String comment, String username, String date) {
        this.rating = rating;
        this.comment = comment;
        //this.username = username;
        //this.date = date;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

}
