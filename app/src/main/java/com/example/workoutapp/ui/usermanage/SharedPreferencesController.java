package com.example.workoutapp.ui.usermanage;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesController {

    private static final String SHARED_PREF = "WorkOutAppSharedPreferences";
    Context ctx;

    public SharedPreferencesController (Context context) {
        this.ctx = context;
    }

    public void storePreferences(String username, String usertoken){

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("u_"+username, username);
        editor.putString("t_"+username, usertoken);
        editor.putString("user_act", username);
        editor.putBoolean("email", true);
        editor.putBoolean("push", true);
        editor.putBoolean("alertFavs", true);
        editor.putBoolean("alertJoins", true);

        editor.apply();
    }

    public void deletePreferences(String username){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("u_"+username);
        editor.remove("t_"+username);
        editor.remove("user_act");
        editor.remove("email");
        editor.remove("push");
        editor.remove("alertFavs");
        editor.remove("alertJoins");

        editor.apply();
    }

    public String loadUserAct(){

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        return sharedPreferences.getString("user_act", "");

    }

    public String loadUserToken(String username){

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        return sharedPreferences.getString("t_"+username, "");
    }

    public void setEmail(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean alert = sharedPreferences.getBoolean("email", true);
        editor.putBoolean("email", !alert);
        editor.apply();
    }

    public Boolean getPush(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("push", true);
    }

    public void setPush(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean alert = sharedPreferences.getBoolean("push", true);
        editor.putBoolean("push", !alert);
        editor.apply();
    }

    public void setAlertFavs(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean alert = sharedPreferences.getBoolean("alertFavs", true);
        editor.putBoolean("alertFavs", !alert);
        editor.apply();
    }

    public Boolean getAlertFavs(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("alertFavs", true);
    }

    public void setAlertJoins(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean alert = sharedPreferences.getBoolean("alertJoins", true);
        editor.putBoolean("alertJoins", !alert);
        editor.apply();
    }

    public Boolean getAlertJoins(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("alertJoins", true);
    }

    public void setNotified(Integer actID, String type){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        String username = loadUserAct();
        String notif = username + actID.toString() + type;
        editor.putBoolean(notif, true);
        editor.apply();
    }

    public Boolean isNotified(Integer actID, String type){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        String username = loadUserAct();
        String notif = username + actID.toString() + type;
        return sharedPreferences.contains(notif);
    }

}
