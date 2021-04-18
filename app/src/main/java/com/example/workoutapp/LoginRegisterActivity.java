package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.example.workoutapp.ui.usermanage.LoginRegisterFragmentManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

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

    }
}