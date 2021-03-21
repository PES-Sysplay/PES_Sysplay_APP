package com.example.workoutapp.ui.home;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutapp.R;

public class HomeViewModel extends RecyclerView.ViewHolder {

    private TextView view;
    public HomeViewModel(@NonNull View itemView) {
        super(itemView);
        view = itemView.findViewById(R.id.activityTitle);
    }

    public TextView getView(){
        return view;
    }
}