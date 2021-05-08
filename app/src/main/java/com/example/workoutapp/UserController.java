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
import com.google.gson.JsonObject;

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

    public void changePassword(String userOldPassword, String userNewPassword, VolleyResponseListener vrl) {
        String changePassURL = URL + "/api/change_password/";
        Map<String, String> params = new HashMap<>();
        params.put("old_password", userOldPassword);
        params.put("new_password", userNewPassword);
        JSONObject jsonBody = new JSONObject(params);
        final String requestBody = jsonBody.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, changePassURL, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("VOLLEY", response.toString());
                vrl.onResponse("Contraseña correctamente cambiada");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                vrl.onError("Error al cambiar la contraseña");
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

    public void google_log_reg(String username, String token, VolleyResponseListener vrl){
        String googleURL = URL + "/api/login/google/?token=" + token;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, googleURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String responseToken = response.getString("token");
                    UserSingleton userSingleton = UserSingleton.setInstance(username, responseToken, ctx);
                    SharedPreferencesController pref_ctrl = new SharedPreferencesController(ctx);
                    pref_ctrl.storePreferences(username, responseToken);
                    vrl.onResponse(responseToken);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                vrl.onError("error con Google");
            }
        });

        RequestSingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    public void getProfile(VolleyResponseListener vrl) {
        String getProfileURL = URL + "/api/me/";
        Map<String, String> params = new HashMap<>();
        final JSONObject[] jsonBody = {new JSONObject(params)};
        final String[] ret = new String[1];

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getProfileURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    ret[0] = response.getString("email");
                    vrl.onResponse(ret[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                vrl.onError("Error al obtener datos del perfil");
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

        RequestSingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }
}

