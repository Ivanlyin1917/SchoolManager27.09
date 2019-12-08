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
                Date titleDate = myCalendar.getDate(position+1);
                Log.i(TAG,"Selected page="+page+", date "+titleDate);
                model.setTitleDate(titleDate);
                model.setPage(page);
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
    /*
    *     private static final String TAG = "Rozklad";
    private static RozkladSharedViewModel model;
    private static final int ROZKLAD_LOADER = 200;
    RozkladCursorAdapter rozkladCursorAdapter;
    private static int pageNum;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rozkladCursorAdapter = new RozkladCursorAdapter(getContext(),null,false);
        View fragmentRozklad = inflater.inflate(R.layout.fragment_rozklad, container, false);
        ViewPager rozklad = fragmentRozklad.findViewById(R.id.rozklad_pager);
//        rozklad.setClipToPadding(false);
        rozklad.setPageMargin(12);
        rozklad.setClipChildren(false);
        rozklad.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            public void onPageSelected (int position){
                pageNum=position+1;
                Log.i (TAG, "selectet page "+pageNum);
                model.setWeekDay(pageNum);
                restartCursorLoader(pageNum);
                model.setRozkladCursorAdapter(rozkladCursorAdapter);
            }
        });

        Bundle bundle=new Bundle();
        bundle.putInt("weekDay",1);
        getLoaderManager().initLoader(ROZKLAD_LOADER,bundle,this);
        model = ViewModelProviders.of(getActivity()).get(RozkladSharedViewModel.class);
        model.setWeekDay(1);
        model.setRozkladCursorAdapter(rozkladCursorAdapter);
        return fragmentRozklad;
    }


    //Методи для загрузки даних CursorLoader
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch (i){
            case ROZKLAD_LOADER:
                //int weekDay = model.getWeekDay();
                int weekDay = bundle.getInt("weekDay");
                CursorLoader cursorLoader = new CursorLoader(getContext(),
                        SchoolManagerContract.LessonsEntry.ROZKLAD_URI,null,null,
                        new String[]{String.valueOf(weekDay)},null);
                Log.i(TAG, "select lessons for day="+weekDay);
                return cursorLoader;
            default: return null;
        }

    }

    @Override
    public void onLoadFinished( Loader<Cursor> loader, Cursor cursor) {
        int id = loader.getId();
        switch (id){
            case ROZKLAD_LOADER:
                rozkladCursorAdapter.swapCursor(cursor);
                break;
        }

    }

    @Override
    public void onLoaderReset( Loader<Cursor> loader) {
        int id = loader.getId();
        switch (id) {
            case ROZKLAD_LOADER:
                rozkladCursorAdapter.swapCursor(null);
                break;
        }
    }

    private void restartCursorLoader(int dayID){
        Bundle bundle=new Bundle();
        bundle.putInt("weekDay",dayID);
        getLoaderManager().restartLoader(ROZKLAD_LOADER,bundle,this);
    }*/


}
