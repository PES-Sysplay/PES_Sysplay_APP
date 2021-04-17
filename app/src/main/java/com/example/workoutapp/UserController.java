package com.example.workoutapp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

public class UserController {

    public static final String QUERY_FOR_LOGIN = "https://pes-workout.herokuapp.com/api/login/";
    //aun no está implementada esta api creo
    public static final String QUERY_FOR_REGISTER = "https://pes-workout.herokuapp.com/api/register/";
    Context ctx;

    public UserController(Context context){
        this.ctx = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(User ret);
    }

    public void logIn(UserController.VolleyResponseListener vrl) {
        String url = QUERY_FOR_LOGIN;

        JsonObjectRequest req = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        User ret = gson.fromJson(response.toString(), User.class);

                        vrl.onResponse(ret);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ctx, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                        vrl.onError("Error al iniciar sesión");
                    }
                });
        RequestSingleton.getInstance(ctx).addToRequestQueue(req);
    }

    public void register(UserController.VolleyResponseListener vrl) {
        String url = QUERY_FOR_REGISTER;

        JsonObjectRequest req = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        User ret = gson.fromJson(response.toString(), User.class);

                        vrl.onResponse(ret);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ctx, "Error al registrarse", Toast.LENGTH_SHORT).show();
                        vrl.onError("Error al registrarse");
                    }
                });
        RequestSingleton.getInstance(ctx).addToRequestQueue(req);
    }
}
