package com.example.workoutapp.ui.usermanage;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

public class LoginRegisterFragmentManager extends FragmentPagerAdapter {

    int totalTabs;

    public LoginRegisterFragmentManager(FragmentManager fm, int totaltabs) {
        super(fm);

        this.totalTabs = totaltabs;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    @NotNull
    public Fragment getItem(int position){
        switch (position){
            case 0:
                return new LoginTabFragment();
            case 1:
                return new RegisterTabFragment();
            default:
                return null;
        }
    }

}
