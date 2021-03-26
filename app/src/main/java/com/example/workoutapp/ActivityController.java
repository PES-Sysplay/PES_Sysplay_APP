package com.example.workoutapp;


import android.content.Context;
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

    public static final String QUERY_FOR_ACTIVITY = "https://dev-pes-workout.herokuapp.com/api/activity/";
    Context ctx;

    public ActivityController(Context context){
        this.ctx = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(ArrayList<Activitat> ret);
    }

    public void getActivitats(VolleyResponseListener vrl) {
        String url = QUERY_FOR_ACTIVITY;
        ArrayList<Activitat> ret = new ArrayList<Activitat>();

        JsonArrayRequest req = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject jsonact = response.getJSONObject(i);
                                Gson gson = new Gson();
                                Activitat act = gson.fromJson(jsonact.toString(), Activitat.class);
                                ret.add(act);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        vrl.onResponse(ret);
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

}
