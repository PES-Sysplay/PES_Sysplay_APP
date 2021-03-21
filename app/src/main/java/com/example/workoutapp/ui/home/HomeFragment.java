package com.example.workoutapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ActivityListAdapter adapter;
    private List<Activitat> activityList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.activityTitle);
        updateList();


        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(new ActivityListAdapter(root.getContext(), activityList));

        return root;
    }

    private void updateList(){
        activityList.add(new Activitat("gym", "descripcion to wapa", "", LocalDateTime.now(), 5, 0, 0, "pepegym"));

        //ObjectMapper mapper = new ObjectMapper();

        //llamada a controlador
        //activityList = mapper.readValue(input, new TypeReference<List<Activitat>>(){});
        //activityLsit = parse(xd) y algo mas


    }
}