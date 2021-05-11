package com.example.workoutapp;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
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
        ArrayList<Activitat> ret = new ArrayList<>();

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
                        Toast.makeText(ctx, "No se han encontrado actividades", Toast.LENGTH_SHORT).show();
                        vrl.onError("No se han encontrado actividades");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
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

    public void getActivitat(int id, VolleyResponseListener vrl) {
        String url = BASE_URL + "/api/activity/" + Integer.toString(id);
        ArrayList<Activitat> ret = new ArrayList<>();

        JsonArrayRequest req = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                            try {
                                JSONObject jsonact = response.getJSONObject(0);
                                Gson gson = new Gson();
                                Activitat act = gson.fromJson(jsonact.toString(), Activitat.class);
                                ret.add(act);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        vrl.onResponseActivity(ret);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ctx, "No se ha encontrado la actividad", Toast.LENGTH_SHORT).show();
                        vrl.onError("No se ha encontrado la actividad");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
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
        String url = BASE_URL + "/api/activitytype";
        //String url = "https://dev-pes-workout.herokuapp.com/api/activitytype";
        ArrayList<String> ret = new ArrayList<String>();

        JsonArrayRequest req = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonact = response.getJSONObject(i);
                                String aux = jsonact.getString("name");
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
    public void joinActivity(Integer activityID, ActivityController.VolleyResponseListener vrl) {
        String joinActURL = BASE_URL + "/api/join/";
        Map<String, Integer> params = new HashMap<>();
        params.put("activity_id", activityID);
        JSONObject jsonBody = new JSONObject(params);
        final String requestBody = jsonBody.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, joinActURL, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                vrl.onResponseJoinActivity();
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
        RequestSingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    public void leftActivity(Integer activityID, ActivityController.VolleyResponseListener vrl) {
        String leaveActURL = BASE_URL + "/api/join/"+activityID+"/";
        //Map<String, String> params = new HashMap<>();
        JSONObject jsonBody = new JSONObject();
        final String requestBody = jsonBody.toString();
        StringRequest request = new StringRequest(Request.Method.DELETE, leaveActURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                vrl.onResponseJoinActivity();

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
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        RequestSingleton.getInstance(ctx).addToRequestQueue(request);
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponseActivity(ArrayList<Activitat> ret);

        void onResponseType(ArrayList<String> ret);

        void onResponseJoinActivity();
    }

}
