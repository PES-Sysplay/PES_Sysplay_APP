package com.example.workoutapp.ui.profile;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class MyActivitiesFragmentManager extends FragmentPagerAdapter {
    private Context context;
    int totalTabs;

    public MyActivitiesFragmentManager (FragmentManager fm, Context ctx, int totaltabs) {
        super(fm);

        this.context = ctx;
        this.totalTabs = totaltabs;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    public Fragment getItem(int position){
        switch (position){
            case 0:
                FutureTabFragment futureTabFragment = new FutureTabFragment();
                return futureTabFragment;
            case 1:
                OldTabFragment oldTabFragment = new OldTabFragment();
                return oldTabFragment;
            default:
                return null;
        }
    }
}
