package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.workoutapp.ui.usermanage.GoogleManager;
import com.example.workoutapp.ui.usermanage.LoginRegisterFragmentManager;
import com.example.workoutapp.ui.usermanage.SharedPreferencesController;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class LoginRegisterActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton bt_google;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        tabLayout = findViewById(R.id.tab_layout);
        bt_google = findViewById(R.id.fab_google);
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

        bt_google.setOnClickListener(v -> {
            int screen = tabLayout.getSelectedTabPosition();
            UserController userController = new UserController(this);
            GoogleManager googleManager = new GoogleManager(this);

            GoogleSignInAccount u;
            u = googleManager.signIn();

            if (screen == 0){
                userController.logIn(u.getDisplayName(), "", new UserController.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(LoginRegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String message) {
                        Intent intent = new Intent(LoginRegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onResponseProfile(ArrayList<String> ret) {

                    }
                });

            } else if (screen == 1) {
                userController.register(u.getEmail(), u.getDisplayName(), "", new UserController.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(LoginRegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String message) {
                        Intent intent = new Intent(LoginRegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onResponseProfile(ArrayList<String> ret) {

                    }
                });
            }
        });


        checkUserActual();

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
        }
    }
}