package com.example.workoutapp.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.Chat;
import com.example.workoutapp.Organizer;
import com.example.workoutapp.R;
import com.example.workoutapp.Review;
import com.example.workoutapp.UserActivityController;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class FavoritesFragment extends Fragment {

    RecyclerView activityListView;
    FavoriteAdapter adapter;
    BottomNavigationView navBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        this.requireActivity().setTitle("Mis favoritos");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_favorites, container, false);

        activityListView = root.findViewById(R.id.recycler_view_fav);

        adapter = FavoriteAdapter.getInstance(root.getContext(), new ArrayList<>());
        activityListView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        activityListView.setAdapter(adapter);

        getFaveActivities();

        activityListView.setVisibility(View.VISIBLE);
        navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);

        return root;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
            NavHostFragment.findNavController(FavoritesFragment.this)
                    .navigate(R.id.action_favorites_fragment_to_navigation_profile);
            navBar.setVisibility(View.VISIBLE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        getFaveActivities();
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
                ArrayList<Activitat> futAux = new ArrayList<>();

                for (Activitat act : ret) {
                    if (!act.isOld()) futAux.add(act);
                }
                adapter.setActivitatsUsuari(futAux);
            }

            @Override
            public void onResponseFav() {
            }

            @Override
            public void onResponseJoinedActivites(ArrayList<Activitat> ret) {
            }

            @Override
            public void onResponseReviewList(ArrayList<Review> ret) {

            }

            @Override
            public void onResponseOrganizationList(ArrayList<Organizer> ret) {

            }

            @Override
            public void onResponseChat(ArrayList<Chat> ret) {

            }

            @Override
            public void onResponseReportReview() {

            }
        });
    }



}
