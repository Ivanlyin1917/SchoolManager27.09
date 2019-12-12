package com.example.myapplication.fragments.homework;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.MyCalendar;
import com.example.myapplication.R;
import com.example.myapplication.adapter.HomeworkCursorAdapter;
import com.example.myapplication.dialog.AddHomeworkFragmentDialog;
import com.example.myapplication.dialog.AddLessonFragmentDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeWorkPageFragment extends Fragment {
    private int pageNumber;
    private String title;
    private static HomeworkSharedViewModel model;
    private ListView hwListView;


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
        hwListView = result.findViewById(R.id.homeworkListView);
        HomeworkCursorAdapter cursorAdapter=model.getHomeworkCursorAdapter();
        hwListView.setAdapter(cursorAdapter);
        TextView pageHeader=result.findViewById(R.id.homework_fragmet_text); //отримуэмо TextView з розмітки фрагмента
        String header = model.getTitleDate();
        pageHeader.setText(header);
        FloatingActionButton fab=getActivity().findViewById(R.id.fab_homework);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                model.setHomework_uri(null);
                AddHomeworkFragmentDialog dlg = new AddHomeworkFragmentDialog();
                dlg.show(getFragmentManager(),"dlg");
            }
        });
        return result;
    }

}
