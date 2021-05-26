package com.example.workoutapp.ui.myactivities;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;


public class MyActivitiesFragmentManager extends FragmentStateAdapter {
    private Context context;
    private static final int totalTabs = 2;

    public MyActivitiesFragmentManager (Fragment frag) {
        super(frag);

        //this.context = ctx;
        //this.totalTabs = totaltabs;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FutureTabFragment();
            case 1:
                return new OldTabFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return totalTabs;
    }
}
