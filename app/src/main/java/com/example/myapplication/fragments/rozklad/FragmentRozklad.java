package com.example.myapplication.fragments.rozklad;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;

public class FragmentRozklad extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentRozklad = inflater.inflate(R.layout.fragment_rozklad, container, false);
        ViewPager rozklad = fragmentRozklad.findViewById(R.id.rozklad_pager);
        rozklad.setAdapter(new RozkladPageFragmentAdapter(getContext(),getFragmentManager()));
        return fragmentRozklad;
    }


}