package com.example.workoutapp.ui.home;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.workoutapp.Activitat;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.workoutapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScrollingActivity extends AppCompatActivity {

    int pos;
    ImageView photo;
    TextView activity,organization, time, place,price, member_price,description;
    List<Activitat> activity_list = new ArrayList<>();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        pos = getIntent().getIntExtra("Position recycler",0);
        // = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
       // toolbar = getSupportActionBar();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);



        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        init_values();
        activity_list = ActivityListAdapter.getInstance(this, new ArrayList<>()).copyInfo();
        set_values();

        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }



    void init_values(){

        activity = findViewById(R.id.activitat_text);
        organization = findViewById(R.id.organization_text);
        time = findViewById(R.id.timehour_text);
        place = findViewById(R.id.place_text);
        price = findViewById(R.id.price_text);
        member_price = findViewById(R.id.price_text2);
        description = findViewById(R.id.description_text);
        photo = findViewById(R.id.imageView);

    }
    void set_values(){
        Log.d("STATE", activity_list.get(pos).getName());
        activity.setText(activity_list.get(pos).getName());
        //organization.setText((activity_list.get(pos).getOrganizerName()));
        time.setText(activity_list.get(pos).getDate_time());
        place.setText(activity_list.get(pos).getLocation());
        price.setText(activity_list.get(pos).getPreu());
        member_price.setText(activity_list.get(pos).getPreuSoci());
        description.setText(activity_list.get(pos).getDescription());
        Picasso.get().load(activity_list.get(pos).getPhoto_url()).into(photo);


    }

}