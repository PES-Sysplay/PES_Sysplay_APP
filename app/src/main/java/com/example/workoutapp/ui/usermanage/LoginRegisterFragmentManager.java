package com.example.workoutapp.ui.usermanage;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginRegisterFragmentManager extends FragmentPagerAdapter {

    private Context context;
    int totalTabs;

    public LoginRegisterFragmentManager (FragmentManager fm, Context ctx, int totaltabs) {
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
                LoginTabFragment loginTabFragment = new LoginTabFragment();
                return loginTabFragment;
            case 1:
                RegisterTabFragment registerTabFragment = new RegisterTabFragment();
                return registerTabFragment;
            default:
                return null;
        }
    }

}
