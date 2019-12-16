package com.example.myapplication.fragments.homework;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;




public class HomeWorkPageFragmentAdapter extends FragmentPagerAdapter {

    private static final String TAG = "Homework";
    private static  int COUNT_PAGE = 7*4;//5 нужно заменить константой из настроек
    private Context context=null;


    public HomeWorkPageFragmentAdapter(Context context,FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return HomeWorkPageFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return COUNT_PAGE;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
       return HomeWorkPageFragment.getTitle(context,position);
    }

    @Override
    public float getPageWidth(int position) {
        return 0.93f;
    }
}
