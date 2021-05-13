package com.example.workoutapp.ui.myactivities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.ActivityController;
import com.example.workoutapp.R;
import com.example.workoutapp.ui.home.ActivityListAdapter;
import com.example.workoutapp.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.Objects;

public class FutureTabFragment extends HomeFragment {

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_act_future, container, false);
        recyclerView = root.findViewById(R.id.recyclerview);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setShowHideAnimationEnabled(false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();

        updateList(root);
        super.updateType(root);

        adapterFuture = ActivityListAdapter.getInstance(root.getContext(), new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(adapterFuture);

        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(privInflater, privContainer, savedInstanceState);
    }

    @Override
    public void updateList(View root) {
        //super.updateList(root);
        adapter = adapterFuture;
        recyclerView.setAdapter(adapterFuture);
        ActivityController dc = new ActivityController(getContext());

        dc.getActivitatFut(new ActivityController.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(root.getContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponseActivity(ArrayList<Activitat> ret) {
                adapterFuture.setList(ret);
                Log.d("STATE", ret.get(0).getDate_time());
                // do things
            }

            @Override
            public void onResponseType(ArrayList<String> ret) {
            }

            @Override
            public void onResponseJoinActivity() {

            }

        });
    }
}
