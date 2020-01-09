package com.example.myapplication.fragments.rozklad;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapter.RozkladCursorAdapter;
import com.example.myapplication.data.SchoolManagerContract;

public class FragmentRozklad extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>  {
    private static final String TAG = "Rozklad";
    private static RozkladSharedViewModel model;
    private static final int ROZKLAD_LOADER = 200;
    RozkladCursorAdapter rozkladCursorAdapter;
    private static int pageNum;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // PageLisener pl = new PageLisener();
        rozkladCursorAdapter = new RozkladCursorAdapter(getContext(),null,false);
        View fragmentRozklad = inflater.inflate(R.layout.fragment_rozklad, container, false);
        ViewPager rozklad = fragmentRozklad.findViewById(R.id.rozklad_pager);
        PagerTabStrip tab = fragmentRozklad.findViewById(R.id.rozklad_tab);
        tab.setTabIndicatorColor(getResources().getColor(R.color.colorAccent));
//        rozklad.setClipToPadding(false);
       // rozklad.setPageMargin(12);
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
        rozklad.setAdapter(new RozkladPageFragmentAdapter(getContext(),getFragmentManager()));
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
        Log.i(TAG,"restart loader for day" + dayID);
    }


}
