package com.example.workoutapp;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
                }) {
                    @Override
                    public Map<String,String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String userToken = UserSingleton.getInstance().getId();
                    Log.d("", "");
                    headers.put("Authorization", "Token " + userToken);
                    return headers;
                    }

                    @Override
                    public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                    }

        };
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
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        String userToken = UserSingleton.getInstance().getId();
                        headers.put("Authorization", "Token " + userToken);
                        return headers;
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }
                };
            RequestSingleton.getInstance(ctx).addToRequestQueue(req);
    }
    //activityID sera la primary key de activity
    public void joinActivity(String activityID, ActivityController.VolleyResponseListener vrl) {
        String joinActURL = BASE_URL + "/api/joinActivity/";
        Map<String, String> params = new HashMap<>();
        params.put("activity_id", activityID);
        JSONObject jsonBody = new JSONObject(params);
        final String requestBody = jsonBody.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, joinActURL, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("VOLLEY", response.toString());
                vrl.onResponseJoinedOrLeft("Te has unido a la actividad");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                vrl.onError("Error al unirse a la actividad");
            }
        }) {
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String userToken = UserSingleton.getInstance().getId();
                Log.d("", "");
                headers.put("Authorization", "Token " + userToken);
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };
    }

    public void leftActivity(String activityID, ActivityController.VolleyResponseListener vrl) {
        String joinActURL = BASE_URL + "/api/unjoinActivity/";
        Map<String, String> params = new HashMap<>();
        params.put("activity_id", activityID);
        JSONObject jsonBody = new JSONObject(params);
        final String requestBody = jsonBody.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, joinActURL, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("VOLLEY", response.toString());
                vrl.onResponseJoinedOrLeft("Te has desapuntado de la actividad");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                vrl.onError("Error al desapuntarse de la actividad");
            }
        }) {
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String userToken = UserSingleton.getInstance().getId();
                Log.d("", "");
                headers.put("Authorization", "Token " + userToken);
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponseJoinedOrLeft(String message);

        void onResponseActivity(ArrayList<Activitat> ret);

        void onResponseType(ArrayList<String> ret);
    }

}