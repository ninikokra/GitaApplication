package com.example.gitaapplication.Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class LoginFragmentAdapter extends FragmentStateAdapter {


    private String[] titles= new String[]{"Login","Sign Up"};

    int totalTabs;

    public LoginFragmentAdapter (@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new LoginTabFragment();
            case 1:
                return new SignUpTabFragment();
            default:
                return null;

        }
    }

    @Override
    public int getItemCount () {
        return titles.length;
    }

}
