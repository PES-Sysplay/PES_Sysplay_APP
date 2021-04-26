package com.example.workoutapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.workoutapp.ui.usermanage.SharedPreferencesController;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class UserController {

    public static final String URL = "https://dev-pes-workout.herokuapp.com";
    Context ctx;

    public UserController(Context context){
        this.ctx = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(String message);
    }

    public void logIn(String userName, String userPassword, VolleyResponseListener vrl) {
        try {
            String loginURL = URL + "/api/login/";
            Map<String, String> params = new HashMap<>();
            params.put("username", userName);
            params.put("password", userPassword);
            JSONObject jsonBody = new JSONObject(params);
            final String requestBody = jsonBody.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, loginURL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("VOLLEY", "successful login");
                    Log.d("register", response.toString());
                    String response_token = null;
                    try {
                        response_token = response.getString("token");

                        UserSingleton us = UserSingleton.setInstance(userName, response_token, ctx);
                        SharedPreferencesController pref_ctrl = new SharedPreferencesController(ctx);
                        pref_ctrl.storePreferences(userName, response_token);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    vrl.onResponse("successful login");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                    String msg = error.toString();
                    vrl.onError(msg);
                }
            }) {
                @Override
                public Map<String,String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    return headers;
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

            };

            RequestSingleton.getInstance(ctx).addToRequestQueue(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void register(String userEmail, String userName, String userPassword, VolleyResponseListener vrl) {
        String registerURL = URL + "/api/client/";
        Map<String, String> params = new HashMap<>();
        params.put("username", userName);
        params.put("email", userEmail);
        params.put("password", userPassword);
        JSONObject jsonBody = new JSONObject(params);
        final String requestBody = jsonBody.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, registerURL, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("VOLLEY", response.toString());
                Log.d("register", response.toString());
                try {

                    String response_username = response.getString("username");
                    String response_token = response.getString("token");


                    UserSingleton us = UserSingleton.setInstance(response_username, response_token, ctx);
                    SharedPreferencesController pref_ctrl = new SharedPreferencesController(ctx);
                    pref_ctrl.storePreferences(userName, response_token);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                vrl.onResponse("success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                String msg = error.toString();
                vrl.onError(msg);
            }
        }) {
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };

        RequestSingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    public void changePassword(String userOldPassword, String userNewPassword) {
        try {
            String changePassURL = URL + "/api/change_password/";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("oldPassword", userOldPassword);
            jsonBody.put("newPassword", userNewPassword);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, changePassURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<String, String>();
                    UserSingleton us = UserSingleton.getInstance();
                    String token = us.getId();
                    header.put("Authorization", "Token "+token);
                    return header;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
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

            RequestSingleton.getInstance(ctx).addToRequestQueue(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
