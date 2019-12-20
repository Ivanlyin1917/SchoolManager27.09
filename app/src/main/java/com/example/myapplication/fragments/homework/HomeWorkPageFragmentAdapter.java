package com.example.myapplication.fragments.homework;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.myapplication.MyCalendar;

import java.text.SimpleDateFormat;
import java.util.Date;


public class HomeWorkPageFragmentAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = "Homework";
    private static  int COUNT_PAGE = 7*4;//5 нужно заменить константой из настроек
    private Context context=null;
    private String tabTitles[]=new String[]{"ПН","ВТ","СР","ЧТ","ПТ","СБ","НД"};


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
        int i = position%7;
        MyCalendar myCalendar = new MyCalendar();
        Date titleDate = myCalendar.getDate(position+1);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.YYYY");
        String dateString = format.format(titleDate);
       //return HomeWorkPageFragment.getTitle(context,position);
        return tabTitles[i]+" "+dateString;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
       if (object instanceof HomeWorkPageFragment){
           return POSITION_UNCHANGED;
       }else{
           return POSITION_NONE;
       }

    }
// @Override
   // public float getPageWidth(int position) {
    //    return 0.93f;
   // }
}
