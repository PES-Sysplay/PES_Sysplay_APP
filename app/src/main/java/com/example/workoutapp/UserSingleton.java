package com.example.workoutapp;

import android.content.Context;

public class UserSingleton {

    private String username;
    private String token_id;
    private static Context ctx;
    private static UserSingleton instance;


    public UserSingleton(String username, String id, Context context){
        this.username = username;
        this.token_id = id;
    }

    public static synchronized UserSingleton setInstance(String username, String id, Context context){
        if (instance == null) {
            instance = new UserSingleton(username, id, context);
        }
        return instance;
    }

    public String getId() {
        return token_id;
    }

    public String getUsername() {
        return username;
    }

    public static UserSingleton getInstance(){ return instance; }
}
