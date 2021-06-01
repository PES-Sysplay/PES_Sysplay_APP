package com.example.workoutapp.ui.myactivities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.ActivityController;
import com.example.workoutapp.ui.home.ActivityListAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.workoutapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.workoutapp.ui.profile.FavoritesFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class MyActivitiesActivity extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    ViewGroup root;
    BottomNavigationView navBar;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        this.requireActivity().setTitle("Mis eventos");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.activity_my_activities, container, false);
        tabLayout = root.findViewById(R.id.tab_layout_act);
        viewPager = root.findViewById(R.id.view_pager_act);

        navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);


        FragmentStateAdapter adapter = new MyActivitiesFragmentManager(this);
        viewPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //escribe(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        String [] tabTtiles={"PENDIENTES","ANTIGUAS"};
        new TabLayoutMediator(tabLayout, viewPager,
                (myTabLayout, position) ->
                myTabLayout.setText(tabTtiles[position])
        ).attach();
//        tabLayout.addTab(tabLayout.newTab().setText("PENDIENTES"));
//        tabLayout.addTab(tabLayout.newTab().setText("ANTIGUAS"));
//        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);
        //final MyActivitiesFragmentManager fragManager = new MyActivitiesFragmentManager(getSupportFragment());


        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        return root;
    }
    

    /*public void escribe(int pos){
        Toast.makeText(this, pos, Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
                NavHostFragment.findNavController(MyActivitiesActivity.this)
                        .navigate(R.id.action_my_activities_fragment_to_navigation_profile);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onDestroy(){
        navBar.setVisibility(View.VISIBLE);
        super.onDestroy();
    }


    public void onBackPressed() {
        Log.d("DFDF", "fdds");
        navBar.setVisibility(View.VISIBLE);
        super.onDestroy();
    }

}
