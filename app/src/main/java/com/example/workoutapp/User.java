package com.example.workoutapp;

public class User {

    private String token;
    private String username;
    private String password;

    public User(String token, String username, String password){
        this.token = token;
        this.username = username;
        this.password = password;
    }

    public String getToken(){
        return this.token;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

}
