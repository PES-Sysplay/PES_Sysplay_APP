package com.example.workoutapp;

public class UserSingleton {

    private String username;
    private String token_id;
    private static UserSingleton instance;
    private boolean link;

    public UserSingleton(String username, String id){
        this.username = username;
        this.token_id = id;
        this.link = false;
    }

    public void setLink(boolean linkAux){
        link = linkAux;
    }

    public boolean getLink(){
        return link;
    }

    public static synchronized UserSingleton setInstance(String username, String id){
        if (instance == null) {
            instance = new UserSingleton(username, id);
        }
        return instance;
    }

    public String getId() {
        return token_id;
    }

    public String getUsername() {
        return username;
    }

    public void destroy(){
        this.username = null;
        this.token_id = null;
        instance = null;
    }

    public static UserSingleton getInstance(){ return instance; }
}
