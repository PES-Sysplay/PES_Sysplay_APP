package com.example.workoutapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.ActivityController;
import com.example.workoutapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ActivityListAdapter adapter;
    private ArrayList<Activitat> activityList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.recyclerview);
        updateList(root);

        adapter = new ActivityListAdapter(root.getContext(),activityList);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(adapter);

        return root;
    }
    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_seach_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);

    }

    private void updateList(View root){
        ActivityController dc = new ActivityController(getContext());

        dc.getActivitats(new ActivityController.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(root.getContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ArrayList<Activitat> ret) {
               adapter.setList(ret);
               Log.d("STATE", ret.get(0).getDate_time());
                // do things
            }
        });

        /*
        activityList.add(new Activitat("Gym", "descripcion to wapa", "", LocalDateTime.now(), 5, 0, 0, "pepegym"));
        activityList.add(new Activitat("Padel", "descripcion to wapassss", "", LocalDateTime.now(), 5, 0, 0, "pepega"));
        activityList.add(new Activitat("Furbo", "descripcion to wapassss", "", LocalDateTime.now(), 5, 0, 0, "pepegym"));
        activityList.add(new Activitat("Furbo", "descripcion to wapassss", "", LocalDateTime.now(), 5, 0, 0, "pepegym"));
        activityList.add(new Activitat("Basquet", "descripcion to wapassss", "", LocalDateTime.now(), 5, 0, 0, "pepegym"));
        activityList.add(new Activitat("Basquet", "descripcion to wapassss", "", LocalDateTime.now(), 5, 0, 0, "pepegym"));
        activityList.add(new Activitat("polo con elefantes", "descripcion to wapassss", "", LocalDateTime.now(), 5, 0, 0, "pepegym"));
        activityList.add(new Activitat("polo con elefantes", "descripcion to wapassss", "", LocalDateTime.now(), 5, 0, 0, "pepegym"));
        activityList.add(new Activitat("domino", "descripcion to wapassss", "", LocalDateTime.now(), 5, 0, 0, "pepegym"));
        activityList.add(new Activitat("Liga de las Leyendas", "descripcion to wapassss", "", LocalDateTime.now(), 5, 0, 0, "pepegym"));*/

        //ObjectMapper mapper = new ObjectMapper();

        //llamada a controlador
        //activityList = mapper.readValue(input, new TypeReference<List<Activitat>>(){});
        //activityLsit = parse(xd) y algo mas


    }


}