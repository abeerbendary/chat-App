package com.example.chatapp.Adapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPageAdapter  extends FragmentPagerAdapter {
    ArrayList<Fragment>fragments;
    ArrayList<String>titles;
    public ViewPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
        this.fragments=new ArrayList<>();
        this.titles=new ArrayList<>();

    }

    public ViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.fragments=new ArrayList<>();
        this.titles=new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    public void addFragment(Fragment fragment, String title){
        fragments.add(fragment);
        titles.add(title);
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
