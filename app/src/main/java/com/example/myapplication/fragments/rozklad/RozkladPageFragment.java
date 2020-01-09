package com.example.myapplication.fragments.rozklad;

import android.arch.lifecycle.ViewModelProviders;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;

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
import com.example.myapplication.R;
import com.example.myapplication.adapter.RozkladCursorAdapter;
import com.example.myapplication.data.SchoolManagerContract;
import com.example.myapplication.dialog.AddLessonFragmentDialog;
import com.example.myapplication.dialog.RozkladContextMenuFragmentDialog;

public class RozkladPageFragment extends Fragment  {

    private static final String TAG = "Rozklad";
    private ListView rozkladList;
    private RozkladSharedViewModel model;
    private  RozkladCursorAdapter rozkladCursorAdapter;


    public static RozkladPageFragment newInstance(int page) {
        RozkladPageFragment fragment = new RozkladPageFragment();
        Bundle args=new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

   /* public static String getTitle(Context context, int position){
       String title=""   ;

       switch (position+1){
           case 1: title ="Понеділок";
                   break;
           case 2: title = "Вівторок";
               break;
           case 3: title = "Середа";
               break;
           case 4: title = "Четвер";
               break;
           case 5: title = "П\'ятниця";
               break;
           case 6: title = "Субота";
               break;
           case 7: title = "Неділя";
               break;
       }
       return  title;
    }*/

    public RozkladPageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(RozkladSharedViewModel.class);
        //Log.i(TAG,"method onCreate for page="+model.getWeekDay());

    }

    @Override
    public void onStart() {
        rozkladCursorAdapter = model.getRozkladCursorAdapter();//отримуємо дані з ViewModel
        super.onStart();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.rozklad_page_fragment, container, false);//створюэмо фрагмент
        rozkladList= result.findViewById(R.id.lessonsListView); //отримуэмо ListView з розмітки фрагмента
        rozkladCursorAdapter = model.getRozkladCursorAdapter();//отримуємо дані з ViewModel
        rozkladList.setAdapter(rozkladCursorAdapter);


        // Listener для кліку по запису з уроком
        rozkladList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri lessonUri = ContentUris.withAppendedId(SchoolManagerContract.LessonsEntry.ROZKLAD_URI,id);
                model.setLessonUri(lessonUri);
                RozkladContextMenuFragmentDialog contextMenu = new RozkladContextMenuFragmentDialog();
                contextMenu.show(getFragmentManager(),"contextMenu");
            }
        });

        //Слушатель для кнопки Add. Вызываем окно для добавления нового урока
        FloatingActionButton fab=getActivity().findViewById(R.id.fab_rozklad);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                model.setLessonUri(null);
                AddLessonFragmentDialog dlg = new AddLessonFragmentDialog();
                dlg.show(getFragmentManager(),"dlg");
               }
        });
        return result;
    }

}

