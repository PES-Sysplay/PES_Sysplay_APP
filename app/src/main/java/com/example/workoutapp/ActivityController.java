package com.example.workoutapp;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ActivityController {

    public static final String BASE_URL = "https://dev-pes-workout.herokuapp.com";

    @SuppressLint("StaticFieldLeak")
    private static Context ctx;

    public ActivityController(Context context) {
        ctx = context;
    }

    public static Context getContext(){
        return ctx;
    }

    public void getActivitats(VolleyResponseListener vrl) {
        String url = BASE_URL + "/api/activity";
        ArrayList<Activitat> ret = new ArrayList<>();

        JsonArrayRequest req = new JsonArrayRequest
                (Request.Method.GET, url, null, response -> {

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
                }, error -> {
                    Toast.makeText(ctx, "No se han encontrado actividades", Toast.LENGTH_SHORT).show();
                    vrl.onError("No se han encontrado actividades");
                }) {
            @Override
            public Map<String, String> getHeaders() {
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
        ArrayList<String> ret = new ArrayList<>();

        JsonArrayRequest req = new JsonArrayRequest
                (Request.Method.GET, url, null, response -> {

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
                }, error -> {
                    Toast.makeText(ctx, "No s'han trobat activitats", Toast.LENGTH_SHORT).show();
                    vrl.onError("No s'han trobat activitats");
                }) {
            @Override
            public Map<String, String> getHeaders() {
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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, joinActURL, jsonBody, response -> vrl.onResponseJoinActivity(), error -> {
            Log.e("VOLLEY", error.toString());
            vrl.onError("Error al unirse a la actividad");
        }) {
            @Override
            public Map<String,String> getHeaders() {
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
        StringRequest request = new StringRequest(Request.Method.DELETE, leaveActURL, response -> vrl.onResponseJoinActivity(), error -> {
            Log.e("VOLLEY", error.toString());
            vrl.onError("Error al desapuntarse de la actividad");
        }) {
            @Override
            public Map<String,String> getHeaders() {
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
                assert response != null;
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        RequestSingleton.getInstance(ctx).addToRequestQueue(request);
    }

    public void dummyCall(ActivityController.VolleyResponseListener vrl) {
        String url = BASE_URL + "/api/activitytype";
        ArrayList<String> ret = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, response -> {
            Log.i("VOLLEY", response);
            if (response.equals("200")) {
                ret.add("success");
            }
            vrl.onResponseType(ret);
        }, error -> {
            Log.e("VOLLEY", error.toString());
            vrl.onError("Verifica tu correo eléctronico");
        }) {
            @Override
            public Map<String,String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String userToken = UserSingleton.getInstance().getId();
                headers.put("Authorization", "Token " + userToken);
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }


            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                assert response != null;
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        RequestSingleton.getInstance(ctx).addToRequestQueue(stringRequest);
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponseActivity(ArrayList<Activitat> ret);

        void onResponseType(ArrayList<String> ret);

        void onResponseJoinActivity();
    }

}
