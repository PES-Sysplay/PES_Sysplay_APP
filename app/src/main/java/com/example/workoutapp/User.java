package com.example.workoutapp;

public class User {

    private String email;
    private String username;
    private String id;
    private String password;
    private Boolean googleSigned; //If true password is empty

    //default constructor
    public User(){
    }

    //constructor for google sign-up
    public User(String email, String username, String id, String password, Boolean googleSigned){
        this.email = email;
        this.username = username;
        this.id = id;
        this.password = password;
        this.googleSigned = googleSigned;
    }

    //constructor for sign-up without google
    public User(String email, String username, String id, Boolean googleSigned){
        this.email = email;
        this.username = username;
        this.id = id;
        this.googleSigned = googleSigned;
    }

    public Boolean getGoogleSigned() {
        return googleSigned;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void changePassword(String newPass){
        this.password = newPass;
    }
}
