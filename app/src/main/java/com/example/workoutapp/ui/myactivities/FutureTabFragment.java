package com.example.workoutapp.ui.myactivities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.ActivityController;
import com.example.workoutapp.Chat;
import com.example.workoutapp.R;
import com.example.workoutapp.UserActivityController;
import com.example.workoutapp.ui.home.ActivityListAdapter;
import com.example.workoutapp.ui.home.HomeFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class FutureTabFragment extends HomeFragment {

    ViewGroup root;
    ArrayList<Activitat> futActivities;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        privInflater = inflater;

        root = (ViewGroup) inflater.inflate(R.layout.fragment_act_future, container, false);

        recyclerView = root.findViewById(R.id.recyclerviewfut);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setShowHideAnimationEnabled(false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();

        super.updateType(root);

        adapter = ActivityListAdapter.getInstance(root.getContext(), new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(adapter);

        return root;
    }

    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

    private void updateList() {
        UserActivityController uc = new UserActivityController(getContext());

        uc.getJoinedActivities(new UserActivityController.VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(String message) {

            }

            @Override
            public void onResponseFavorites(ArrayList<Activitat> ret) {

            }

            @Override
            public void onResponseFav() {

            }

            @Override
            public void onResponseJoinedActivites(ArrayList<Activitat> ret) {
                futActivities = ret;
                displayFutAct();
            }

            @Override
            public void onResponseChat(ArrayList<Chat> ret) {

            }
        });
    }

    private void displayFutAct() {
        ArrayList<Activitat> futAux = new ArrayList<>();

        Date date = Calendar.getInstance().getTime();
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTime(date);

        currentTime.set(Calendar.HOUR_OF_DAY, 0);
        currentTime.set(Calendar.MINUTE, 0);
        currentTime.set(Calendar.SECOND, 0);

        if (futActivities != null){
            for (Activitat act : futActivities) {

                Calendar dateAux = Calendar.getInstance();
                dateAux.setTimeInMillis(act.getTimestamp() * 1000L); //time in ms
                dateAux.set(Calendar.HOUR_OF_DAY, 0);
                dateAux.set(Calendar.MINUTE, 0);
                dateAux.set(Calendar.SECOND, 0);
                if (dateAux.after(currentTime)) futAux.add(act);
                adapter.setList(futAux);
            }
        }
    }
}
