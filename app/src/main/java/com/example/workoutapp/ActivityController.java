package com.example.workoutapp;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ActivityController {

    public static final String BASE_URL = "https://dev-pes-workout.herokuapp.com";

    Context ctx;

    public ActivityController(Context context) {
        this.ctx = context;
    }

    public void getActivitats(VolleyResponseListener vrl) {
        String url = BASE_URL + "/api/activity";
        ArrayList<Activitat> ret = new ArrayList<Activitat>();

        JsonArrayRequest req = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonact = response.getJSONObject(i);
                                Gson gson = new Gson();
                                Activitat act = gson.fromJson(jsonact.toString(), Activitat.class);
                                ret.add(act);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        vrl.onResponseActivity(ret);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ctx, "No s'han trobat activitats", Toast.LENGTH_SHORT).show();
                        vrl.onError("No s'han trobat activitats");
                    }
                });
        RequestSingleton.getInstance(ctx).addToRequestQueue(req);
    }

    public void getActivityTypes(VolleyResponseListener vrl) {
        //String url = BASE_URL + "/api/activitytype";
        String url = "https://dev-pes-workout.herokuapp.com/api/activitytype"; //TODO quitar esto cuando hagamos el merge a master
        ArrayList<String> ret = new ArrayList<String>();

        JsonArrayRequest req = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonact = response.getJSONObject(i);
                                String aux = jsonact.getString("name");
                                Log.d("EY", aux);
                                ret.add(aux);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        vrl.onResponseType(ret);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ctx, "No s'han trobat activitats", Toast.LENGTH_SHORT).show();
                        vrl.onError("No s'han trobat activitats");
                    }
                });
        RequestSingleton.getInstance(ctx).addToRequestQueue(req);
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponseActivity(ArrayList<Activitat> ret);

        void onResponseType(ArrayList<String> ret);
    }

}