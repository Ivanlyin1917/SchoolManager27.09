package com.example.myapplication.fragments.rozklad;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.preference.PreferenceManager;
import android.widget.TextView;

import com.example.myapplication.fragments.rozklad.RozkladPageFragment;

public class RozkladPageFragmentAdapter extends FragmentPagerAdapter {

    private Context context=null;
    private int countdayschool;

    public RozkladPageFragmentAdapter(Context context,FragmentManager fm) {
        super(fm);
        this.context = context;
        setCountSchoolDay(PreferenceManager.getDefaultSharedPreferences(context));
    }

    @Override
    public Fragment getItem(int i) {
        return RozkladPageFragment.newInstance(i);
    }

    @Override
    public int getCount() {

        return countdayschool ;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return RozkladPageFragment.getTitle(context,position);
    }

    @Override
    public float getPageWidth(int position) {
        return 0.97f;
    }
    private void setCountSchoolDay(SharedPreferences sp){
        countdayschool = Integer.valueOf( sp.getString("count_school_day","5"));


    }
}
