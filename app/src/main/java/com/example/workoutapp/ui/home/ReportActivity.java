package com.example.workoutapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.workoutapp.R;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        pos = getIntent().getIntExtra("Position recycler",0);

        Spinner reportSpinner = (Spinner) findViewById(R.id.ReportType);

        ArrayList<String> elements = new ArrayList<>();

        elements.add("Incumplimiento normativa COVID-19");
        elements.add("Publicidad engañosa");
        elements.add("Problemas con la organización");
        elements.add("Otro motivo");

        ArrayAdapter adp = new ArrayAdapter(ReportActivity.this, android.R.layout.simple_spinner_dropdown_item, elements);

        reportSpinner.setAdapter(adp);

        reportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}