package com.example.workoutapp.ui.userfeedback;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workoutapp.R;

public class ReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        getSupportFragmentManager().beginTransaction().add(R.id.reviewContainer, new ReviewFragment()).commit();
    }

    public void goToConfirmedFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.reviewContainer, new ReviewConfirmedFragment()).commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}