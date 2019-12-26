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
import android.support.v4.view.PagerTabStrip;
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
   // private MyCalendar myCalendar = new MyCalendar();
    private SimpleDateFormat format;
    private int page;
    private HomeworkSharedViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentHomework = inflater.inflate(R.layout.fragment_homework, container, false);
        ViewPager homeWork = fragmentHomework.findViewById(R.id.homework_pager);
        format = new SimpleDateFormat("dd.MM.YYYY");
        //homeWork.setClipToPadding(false);
       // homeWork.setPageMargin(12);
        homeWork.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }
            @Override
            public void onPageSelected(int position) {
                page = position+1;
               // SimpleDateFormat format = new SimpleDateFormat("dd.MM.YYYY");
                Log.i(TAG,"Selected page="+page+", date "+format.format(new MyCalendar().getDate(page)));
                model.setPage(page);
                restarCursorLoader(format.format(new MyCalendar().getDate(page)));
                model.setHomeworkCursorAdapter(homeworkCursorAdapter);
                model.setCrnDate(new MyCalendar().getDate(page));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        MyCalendar myCalendar = new MyCalendar();
        HomeWorkPageFragmentAdapter pageAdapter = new HomeWorkPageFragmentAdapter(getContext(),getFragmentManager());
        homeWork.setAdapter(pageAdapter);
        pageAdapter.setPrimaryItem(null,myCalendar.getCurrentPosition(),null);
        model = ViewModelProviders.of(getActivity()).get(HomeworkSharedViewModel.class);
        //Початкові налаштування
        model.setPage(1);
        model.setCrnDate(new MyCalendar().getDate(1));
        Bundle bundle = new Bundle();
        bundle.putString("crnDate",format.format(myCalendar.getDate(1)));
        homeworkCursorAdapter = new HomeworkCursorAdapter(getContext(),null,false);
        getLoaderManager().initLoader(HOMEWORK_LOADER,bundle,this);
        model.setHomeworkCursorAdapter(homeworkCursorAdapter);
        return fragmentHomework;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
       String crtDate = bundle.getString("crnDate");
        CursorLoader cursorLoader = new CursorLoader(getContext(),
                SchoolManagerContract.HomeworksEntry.HOMEWORK_URI,null,null,
                new String[]{crtDate},null);
        Log.i(TAG, "select HomeWork for date="+crtDate);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        homeworkCursorAdapter.swapCursor(cursor);
        if (cursor.moveToFirst()){
           int count_rec=0;
            do {
                count_rec++;
            }while (cursor.moveToNext());
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        homeworkCursorAdapter.swapCursor(null);

    }

    private void restarCursorLoader( String crnDate){
       Bundle bundle = new Bundle();
       bundle.putString("crnDate",crnDate);
        Log.i(TAG, "restart cursor HomeWork for date="+crnDate);
       getLoaderManager().restartLoader(HOMEWORK_LOADER,bundle,this);
    }
}
