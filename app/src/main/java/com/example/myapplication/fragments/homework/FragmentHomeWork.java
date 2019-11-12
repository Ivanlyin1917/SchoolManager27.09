package com.example.myapplication.fragments.homework;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.MyCalendar;
import com.example.myapplication.R;

import java.util.Date;

public class FragmentHomeWork extends Fragment {

    private final String TAG = "Homework";
    private MyCalendar myCalendar = new MyCalendar();
    private int page;
    private HomeworkSharedViewModel model;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentHomework = inflater.inflate(R.layout.fragment_homework, container, false);

        ViewPager homeWork = fragmentHomework.findViewById(R.id.homework_pager);
        homeWork.setClipToPadding(false);
        homeWork.setPageMargin(12);
        homeWork.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                page = position+1;
                //int currentDay = myCalendar.getCurrentDay();
                Date titleDate = myCalendar.getDate(position+1);
                Log.i(TAG,"Selected page="+page+", date "+titleDate);
                model.setTitleDate(titleDate);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        homeWork.setAdapter(new HomeWorkPageFragmentAdapter(getContext(),getFragmentManager()));
        model = ViewModelProviders.of(getActivity()).get(HomeworkSharedViewModel.class);
        model.setTitleDate(myCalendar.getCurrentDate());
        return fragmentHomework;
    }


}
