package com.example.workoutapp;

import android.media.Image;

public class User {

    private String token;
    private String username;
    private String password;

    public User(String token, String username, String password){
        this.token = token;
        this.username = username;
        this.password = password;
<<<<<<< HEAD
        this.googleSigned = googleSigned;
    }

    //constructor for sign-up without google
    public User(String email, String username, String id, Boolean googleSigned){
        this.email = email;
        this.username = username;
        this.id = id;
        this.googleSigned = googleSigned;
        //this.profilePic = defaultavatar.png;
=======
>>>>>>> 5a61227f9671f2d1d927377b47466866a2dff365
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
