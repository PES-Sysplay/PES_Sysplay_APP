package com.example.workoutapp;

import android.content.Context;

public class UserSingleton {

    private String username;
    private String id;
    private String email;
    private static Context ctx;
    private static UserSingleton instance;


    //constructor for google sign-up
    public UserSingleton(String username, String id, Context context){
        this.username = username;
        this.id = id;
    }

    public static synchronized UserSingleton getInstance(String username, String id, String password, Context context){
        if (instance == null) {
            instance = new UserSingleton(username, id, context);
        }
        return instance;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public static UserSingleton getInstance(){ return instance; }
}
