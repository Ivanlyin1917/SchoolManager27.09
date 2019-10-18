package com.example.myapplication.fragments;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class HomeWorkPageFragmentAdapter extends FragmentPagerAdapter {
    private Context context=null;

    public HomeWorkPageFragmentAdapter(Context context,FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        return HomeWorkPageFragment.newInstance(i);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return HomeWorkPageFragment.getTitle(context,position);
    }
}
