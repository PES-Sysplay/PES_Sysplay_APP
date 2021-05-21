package com.example.workoutapp.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.R;
import com.example.workoutapp.UserActivityController;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    RecyclerView activityListView;
    TextView title;
    FavoriteAdapter adapter;
    ArrayList<Activitat>  activityList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_favorites, container, false);

        activityListView = root.findViewById(R.id.recycler_view_fav);
        title = root.findViewById(R.id.title_favorite);

        adapter = FavoriteAdapter.getInstance(root.getContext(), new ArrayList<>());
        activityListView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        activityListView.setAdapter(adapter);

        getFaveActivities();

        activityListView.setVisibility(View.VISIBLE);

        return root;
    }

    private void getFaveActivities() {
        UserActivityController dc = new UserActivityController(getContext());

        dc.getFavorites(new UserActivityController.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String message) {
            }

            @Override
            public void onResponseFavorites(ArrayList<Activitat> ret) {
                activityList = ret;
                adapter.setActivitatsUsuari(activityList);
            }

            @Override
            public void onResponseFav() {
            }

            @Override
            public void onResponseJoinedActivites(ArrayList<Activitat> ret) {
            }
        });
    }



}
