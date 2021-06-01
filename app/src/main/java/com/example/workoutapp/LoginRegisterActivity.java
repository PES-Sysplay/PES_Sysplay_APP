package com.example.workoutapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.workoutapp.ui.home.ActivityListAdapter;
import com.example.workoutapp.ui.usermanage.LoginRegisterFragmentManager;
import com.example.workoutapp.ui.usermanage.SharedPreferencesController;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.workoutapp.ActivityController.getContext;

public class LoginRegisterActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;
    Uri uri =null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_WorkOut_NoActionBar);
        super.onCreate(savedInstanceState);
        uri = getIntent().getData();


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
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final LoginRegisterFragmentManager fragManager = new LoginRegisterFragmentManager(getSupportFragmentManager(), tabLayout.getTabCount());
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
                .requestIdToken(getString(R.string.google_client))
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
            handleSignInResult(task);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
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
                .addOnCompleteListener(this, task -> {
                    // ...
                });
    }

    private void checkUserActual() {
        SharedPreferencesController pref_ctrl = new SharedPreferencesController(LoginRegisterActivity.this);

        String user_act = pref_ctrl.loadUserAct();

        if (!user_act.equals("")){
            String token;
            token = pref_ctrl.loadUserToken(user_act);


            UserSingleton us = UserSingleton.setInstance(user_act, token);

            if (!us.getId().equals("") && !us.getUsername().equals("")) {
                String param;
                Intent intent = new Intent(LoginRegisterActivity.this, MainActivity.class);

                if(uri != null){
                    List<String> parameters = uri.getPathSegments();
                    param = parameters.get(parameters.size() - 1);
                    intent.putExtra("Link", Integer.valueOf(param));
                    us.setLink(true);
                    uri = null;
                }
                else{
                    us.setLink(false);
                }
                startActivity(intent);
                finish();
            }
        } else {
            signOut();
        }
    }
}