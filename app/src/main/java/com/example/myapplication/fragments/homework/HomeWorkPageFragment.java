package com.example.myapplication.fragments.homework;


import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.MyCalendar;
import com.example.myapplication.R;
import com.example.myapplication.adapter.HomeworkCursorAdapter;
import com.example.myapplication.data.SchoolManagerContract.*;
import com.example.myapplication.dialog.AddHomeworkFragmentDialog;
import com.example.myapplication.dialog.HomeworkContextMenuFragmentDialog;


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

//

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

        // Listener для кліку по запису з ДЗ
        hwListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri hwUri = ContentUris.withAppendedId(HomeworksEntry.HOMEWORK_URI,id);
                model.setHomework_uri(hwUri);
                HomeworkContextMenuFragmentDialog contextMenu = new HomeworkContextMenuFragmentDialog();
                contextMenu.show(getFragmentManager(),"contextMenu");
            }
        });
        //TextView pageHeader=result.findViewById(R.id.homework_fragmet_text); //отримуэмо TextView з розмітки фрагмента
       // String header = model.getTitleDate();
       // pageHeader.setText(header);
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
