package com.example.workoutapp.ui.userfeedback;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workoutapp.R;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        getSupportFragmentManager().beginTransaction().add(R.id.reportContainer, new ReportFragment()).commit();
    }

    public void goToSecondFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.reportContainer, new ReportConfirmedFragment()).commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
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