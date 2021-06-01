package com.example.workoutapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.workoutapp.ui.usermanage.SharedPreferencesController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

        void onResponseProfile(ArrayList<String> ret);
    }

    public void logIn(String userName, String userPassword, VolleyResponseListener vrl) {
        try {
            String loginURL = URL + "/api/login/";
            Map<String, String> params = new HashMap<>();
            params.put("username", userName);
            params.put("password", userPassword);
            JSONObject jsonBody = new JSONObject(params);

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, loginURL, jsonBody, response -> {
                Log.i("VOLLEY", "successful login");
                Log.d("register", response.toString());
                String response_token;
                try {
                    response_token = response.getString("token");

                    SharedPreferencesController pref_ctrl = new SharedPreferencesController(ctx);
                    pref_ctrl.storePreferences(userName, response_token);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                vrl.onResponse("successful login");
            }, error -> {
                Log.e("VOLLEY", error.toString());
                String msg = error.toString();
                vrl.onError(msg);
            }) {
                @Override
                public Map<String,String> getHeaders() {
                    return new HashMap<>();
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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, registerURL, jsonBody, response -> {
            Log.i("VOLLEY", response.toString());
            Log.d("register", response.toString());
            try {

                String response_token = response.getString("token");

                SharedPreferencesController pref_ctrl = new SharedPreferencesController(ctx);
                pref_ctrl.storePreferences(userName, response_token);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            vrl.onResponse("success");
        }, error -> {
            Log.e("VOLLEY", error.toString());
            String msg = error.toString();
            vrl.onError(msg);
        }) {
            @Override
            public Map<String,String> getHeaders() {
                return new HashMap<>();
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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, changePassURL, jsonBody, response -> vrl.onResponse("Contraseña cambiada correctamente"), error -> {
            Log.e("VOLLEY", error.toString());
            vrl.onError("Error al cambiar la contraseña");
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
                return "application/json";
            }

        };

        RequestSingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    public void google_log_reg(String username, String token, VolleyResponseListener vrl){
        String googleURL = URL + "/api/login/google/?token=" + token;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, googleURL, null, response -> {
            try {
                String responseToken = response.getString("token");
                SharedPreferencesController pref_ctrl = new SharedPreferencesController(ctx);
                pref_ctrl.storePreferences(username, responseToken);
                vrl.onResponse(responseToken);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> vrl.onError("error con Google"));

        RequestSingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    public void getProfile(VolleyResponseListener vrl) {
        String getProfileURL = URL + "/api/me/";
        ArrayList<String> ret = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getProfileURL, null, response -> {
            try {
                String email = response.getString("email");
                ret.add(email);
                String favs = response.getString("favorites");
                ret.add(favs);
                String joined = response.getString("joined");
                ret.add(joined);
                Boolean hasNotif = response.getBoolean("notifications");
                String notif = Boolean.toString(hasNotif);
                ret.add(notif);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            vrl.onResponseProfile(ret);
        }, error -> {
            Log.e("VOLLEY", error.toString());
            vrl.onError("Error al obtener datos del perfil");
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

        RequestSingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    public void deleteUser(VolleyResponseListener vrl) {
        String deleteURL = URL + "/api/me/";

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, deleteURL, response -> {
            Log.i("VOLLEY", response);
            vrl.onResponse("El usuario se ha eliminado correctamente");
        }, error -> {
            Log.e("VOLLEY", error.toString());
            vrl.onError("Error al eliminar el usuario");
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

    public void putNofications(Boolean email, VolleyResponseListener vrl) {
        String notifsURL = URL + "/api/me/";
        Map<String, Boolean> params = new HashMap<>();
        params.put("email_notifications", email);
        JSONObject jsonBody = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, notifsURL, jsonBody, response -> vrl.onResponse("Notificaciones actualizadas"), error -> {
            Log.e("VOLLEY", error.toString());
            vrl.onError("Error al cambiar los ajustes de notificaciones");
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
                return "application/json";
            }

        };

        RequestSingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }
}

