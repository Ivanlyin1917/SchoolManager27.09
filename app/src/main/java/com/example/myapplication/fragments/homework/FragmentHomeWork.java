package com.example.myapplication.fragments.homework;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;

public class FragmentHomeWork extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentHomework = inflater.inflate(R.layout.fragment_homework, container, false);
        ViewPager rozklad = fragmentHomework.findViewById(R.id.homework_pager);
        rozklad.setAdapter(new HomeWorkPageFragmentAdapter(getContext(),getFragmentManager()));
        return fragmentHomework;
    }


}
