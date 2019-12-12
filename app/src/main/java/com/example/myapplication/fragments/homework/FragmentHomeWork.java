package com.example.myapplication.fragments.homework;

import android.arch.lifecycle.ViewModelProviders;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.MyCalendar;
import com.example.myapplication.R;
import com.example.myapplication.adapter.HomeworkCursorAdapter;
import com.example.myapplication.data.SchoolManagerContract;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentHomeWork extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private final String TAG = "Homework";
    private static final int HOMEWORK_LOADER = 100;
    private HomeworkCursorAdapter homeworkCursorAdapter;
    private MyCalendar myCalendar = new MyCalendar();
    private Date crnDate;
    private int page;
    private HomeworkSharedViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentHomework = inflater.inflate(R.layout.fragment_homework, container, false);
        //TextView pageHeader=getActivity().findViewById(R.id.homework_fragmet_text);

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
                crnDate = myCalendar.getDate(page);
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.YYYY");
                String dateString = getTitlePage(page);
                Log.i(TAG,"Selected page="+page+", date "+format.format(crnDate));
                model.setTitleDate(dateString);
                model.setPage(page);
                model.setCrnDate(crnDate);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        homeWork.setAdapter(new HomeWorkPageFragmentAdapter(getContext(),getFragmentManager()));
        model = ViewModelProviders.of(getActivity()).get(HomeworkSharedViewModel.class);
        //Початкові налаштування
        model.setTitleDate(getTitlePage(1));
        model.setPage(1);
        model.setCrnDate(myCalendar.getDate(1));
        getLoaderManager().initLoader(HOMEWORK_LOADER,null,this);
        model.setHomeworkCursorAdapter(homeworkCursorAdapter);
        return fragmentHomework;
    }
    /*
    *
//
        rozklad.setClipChildren(false);


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

    private String getTitlePage(int page){
        String title="";
        MyCalendar myCalendar = new MyCalendar();
        Log.i("Homework", "Current day = "+myCalendar.getCurrentDay());
        Date titleDate = myCalendar.getDate(page);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.YYYY");
        String dateString = format.format(titleDate);
        int weekDay = page%7;
        switch (weekDay){
            case 1: title = "Понеділок "+dateString;
                break;
            case 2: title = "Вівторок "+dateString;
                break;
            case 3: title = "Середа "+dateString;
                break;
            case 4: title = "Четвер "+dateString;
                break;
            case 5: title = "П\'ятниця "+dateString;
                break;
            case 6: title = "Субота "+dateString;
                break;
            case 0: title = "Неділя "+dateString;
                break;
        }
        return  title;
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        Date crsDate = model.getCrnDate();
        CursorLoader cursorLoader = new CursorLoader(getContext(),
                SchoolManagerContract.HomeworksEntry.HOMEWORK_URI,null,null,
                new String[]{String.valueOf(crsDate)},null);
        Log.i(TAG, "select HomeWork for date="+new SimpleDateFormat("dd.mm.YYYY").format(crsDate));
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
