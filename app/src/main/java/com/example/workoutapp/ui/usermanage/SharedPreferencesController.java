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

}
