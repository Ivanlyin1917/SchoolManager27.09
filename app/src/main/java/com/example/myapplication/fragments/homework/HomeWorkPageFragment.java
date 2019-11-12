package com.example.myapplication.fragments.homework;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.MyCalendar;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeWorkPageFragment extends Fragment {
    private int pageNumber;
    private String title;
    private static HomeworkSharedViewModel model;


    public static HomeWorkPageFragment newInstance(int page) {
       HomeWorkPageFragment fragment = new HomeWorkPageFragment();
        Bundle args=new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    public static String getTitle(Context context, int position){
        String title="";
        MyCalendar myCalendar = new MyCalendar();
        Log.i("Homework", "Current day = "+myCalendar.getCurrentDay());
        Date titleDate = myCalendar.getDate(position+1);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.YYYY");
        String dateString = format.format(titleDate);
        int weekDay = (position+1)%7;
        switch (weekDay){
            case 1: title = "Понеділок "+dateString;
                break;
            case 2: title = "Вівторок"+dateString;
                break;
            case 3: title = "Середа"+dateString;
                break;
            case 4: title = "Четвер"+dateString;
                break;
            case 5: title = "П\'ятниця"+dateString;
                break;
            case 6: title = "Субота"+dateString;
                break;
            case 0: title = "Неділя"+dateString;
                break;
        }
        return  title;
    }

    public HomeWorkPageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt("num",0);
        model = ViewModelProviders.of(getActivity()).get(HomeworkSharedViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.homework_page_fragment, container, false);//створюэмо фрагмент
        TextView pageHeader=(TextView)result.findViewById(R.id.homework_fragmet_text); //отримуэмо TextView з розмітки фрагмента
        String header = String.format("Фрагмент %d", pageNumber+1);
        pageHeader.setText(header);
        return result;
    }
}
