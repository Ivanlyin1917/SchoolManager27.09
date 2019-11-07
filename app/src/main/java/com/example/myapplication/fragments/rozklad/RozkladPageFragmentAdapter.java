package com.example.myapplication.fragments.rozklad;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.myapplication.fragments.rozklad.RozkladPageFragment;

public class RozkladPageFragmentAdapter extends FragmentPagerAdapter {

    private Context context=null;

    public RozkladPageFragmentAdapter(Context context,FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        return RozkladPageFragment.newInstance(i);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return RozkladPageFragment.getTitle(context,position);
    }
}
