package com.example.myapplication.fragments.rozklad;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.preference.PreferenceManager;

import com.example.myapplication.adapter.SmartFragmentStatePagerAdapter;


public class RozkladPageFragmentAdapter extends SmartFragmentStatePagerAdapter {


    private Context context=null;
    private int countdayschool;
    private String tabTitles[]=new String[]{"Неділя","Понеділок","Вівторок","Середа","Четвер","П\'ятниця","Субота"};


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
        //return RozkladPageFragment.getTitle(context,position);
       int i = (position+1) % 7;
        String title = tabTitles[i];
        return title;
    }

   /* @Override
    public float getPageWidth(int position) {
        return 0.97f;
    }*/
    private void setCountSchoolDay(SharedPreferences sp){
        countdayschool = Integer.valueOf( sp.getString("count_school_day","5"));


    }

    public int getItemPosition(@NonNull Object object) {
        if (object instanceof RozkladPageFragment){
            return POSITION_UNCHANGED;
        }else{
            return POSITION_NONE;
        }
}
}
