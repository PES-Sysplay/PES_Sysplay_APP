package com.example.workoutapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.workoutapp.ui.usermanage.LoginRegisterFragmentManager;
import com.example.workoutapp.ui.usermanage.SharedPreferencesController;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class LoginRegisterActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_WorkOut_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        setLayout();

        setGoogle();

        checkUserActual();
    }


    private void setLayout(){
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("INICIAR SESIÓN"));
        tabLayout.addTab(tabLayout.newTab().setText("REGISTRAR"));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);

        final LoginRegisterFragmentManager fragManager = new LoginRegisterFragmentManager(getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(fragManager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //haha lol
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //haha lol
            }
        });
    }

    private void setGoogle(){

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_maps_key))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }



    public void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task, this);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask, Context ctx) {
        try {
           GoogleSignInAccount account = completedTask.getResult(ApiException.class);

           UserController userController = new UserController(this);

           userController.google_log_reg(account.getEmail(), account.getIdToken(), new UserController.VolleyResponseListener() {
               @Override
               public void onError(String message) {

               }

               @Override
               public void onResponse(String message) {
                   launchMainActivity();
               }

                @Override
                public void onResponseProfile(ArrayList<String> ret) {

                }
           });

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("GM", "signInResult:failed code=" + e.getStatusCode());
        }

    }

    public void setTabLayoutAfterRegister() {
        viewPager.setCurrentItem(0);
        Toast.makeText(this, "Verifica tu correo eléctronico", Toast.LENGTH_LONG).show();
    }


    private void launchMainActivity(){
        Intent intent = new Intent(LoginRegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    private void checkUserActual() {
        SharedPreferencesController pref_ctrl = new SharedPreferencesController(LoginRegisterActivity.this);

        String user_act = pref_ctrl.loadUserAct();

        if (!user_act.equals("")){
            String token;
            token = pref_ctrl.loadUserToken(user_act);


            UserSingleton us = UserSingleton.setInstance(user_act, token,LoginRegisterActivity.this);

            if (!us.getId().equals("") && !us.getUsername().equals("")) {
                Intent intent = new Intent(LoginRegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            signOut();
        }
    }
}