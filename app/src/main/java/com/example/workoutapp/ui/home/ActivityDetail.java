package com.example.workoutapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.widget.Toolbar;
import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.workoutapp.Activitat;
import com.example.workoutapp.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ActivityDetail extends Fragment {
    int pos;
    ImageView photo;
    TextView activity, time, place,price,description;
    List<Activitat> activity_list = new ArrayList<>();
    private ActivityListAdapter adapter;

    public static ActivityDetail newInstance() {
        return new ActivityDetail();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_activitydetail, menu);

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.activity_detail, container, false);


        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


        //Toolbar mToolbar = root.findViewById(R.id.toolbar);
       // ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        init_values(root);
        activity_list = ActivityListAdapter.getInstance(root.getContext(), new ArrayList<>()).copyInfo();
        set_values();

        return root;
    }
    void init_values(View root){

        activity = root.findViewById(R.id.activitat_text);
        time = root.findViewById(R.id.timehour_text);
        place = root.findViewById(R.id.place_text);
        price = root.findViewById(R.id.price_text);
        description = root.findViewById(R.id.description_text);
        photo = root.findViewById(R.id.imageView);

    }
    public void setpos(int position){
        pos = position;
    }
    void set_values(){
        Log.d("STATE", activity_list.get(pos).getName());
        activity.setText(activity_list.get(pos).getName());
        time.setText(activity_list.get(pos).getDate_time());
        place.setText(activity_list.get(pos).getLocation());
        price.setText(activity_list.get(pos).getPreu());
        description.setText(activity_list.get(pos).getDescription());
        Picasso.get().load(activity_list.get(pos).getPhoto_url()).into(photo);


    }


    }