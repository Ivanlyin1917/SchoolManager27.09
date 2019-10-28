package com.example.myapplication.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.example.myapplication.Model.DialogScreen;
import com.example.myapplication.R;
import com.example.myapplication.data.SchoolManagerContract;

import java.util.ArrayList;
import java.util.List;

public class RozkladPageFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ROZKLAD_LOADER = 200;
    private static final int SUBJECT_LOADER = 300;
    RozkladCursorAdapter rozkladCursorAdapter;
    private int pageNumber;
    private List<RozkladViewItem> listLessons = new ArrayList<>();
    private  ArrayAdapter<String> subjectListAdapter;
    ListView rozkladList;

    public static RozkladPageFragment newInstance(int page) {
        RozkladPageFragment fragment = new RozkladPageFragment();
        Bundle args=new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    public static String getTitle(Context context, int position){
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
    }

    public RozkladPageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.rozklad_page_fragment, container, false);//створюэмо фрагмент
        rozkladList= result.findViewById(R.id.lessonsListView); //отримуэмо ListView з розмітки фрагмента
        rozkladCursorAdapter = new RozkladCursorAdapter(getContext(),null,false);
        rozkladList.setAdapter(rozkladCursorAdapter);
        //Слушатель для кнопки Add. Вызываем окно для добавления нового урока
        FloatingActionButton fab=getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openDialogAddNewLessons();

               // AlertDialog dialog = DialogScreen.getDialog(getActivity(),1);
               // AutoCompleteTextView subject = dialog.findViewById(R.id.new_lesson_name);
               // subject.setAdapter(subjectListAdapter);
               // dialog.show();


            }
        });

        getLoaderManager().initLoader(ROZKLAD_LOADER,null,this);
        return result;
    }



    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        switch (i){
            case ROZKLAD_LOADER:
                CursorLoader cursorLoader = new CursorLoader(getContext(),
                        SchoolManagerContract.LessonsEntry.ROZKLAD_URI,null,null,new String[]{String.valueOf(pageNumber+1)},null);
                return cursorLoader;
            case SUBJECT_LOADER:
                String[] projection = new String[]{SchoolManagerContract.SubjectEntry.KEY_ID, SchoolManagerContract.SubjectEntry.KEY_NAME};
                String selection=null;
                String[] selectionArgs=null;
                CursorLoader cursorLoaderSubject = new CursorLoader(getContext(),
                        SchoolManagerContract.SubjectEntry.SUBJECT_URI,projection,selection,selectionArgs,null);
                return cursorLoaderSubject;
            default: return null;
        }

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        rozkladCursorAdapter.swapCursor(cursor);
        //для списку в діалоговому вікні
        List<String> subjectList = new ArrayList<>();
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                subjectList.add(cursor.getString(cursor.getColumnIndexOrThrow(
                        SchoolManagerContract.SubjectEntry.KEY_NAME
                )));
                cursor.moveToNext();
            }
           subjectListAdapter= new ArrayAdapter<String>(getActivity(),
                   android.R.layout.simple_dropdown_item_1line, subjectList);
        }
        //----------------------------------------
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        rozkladCursorAdapter.swapCursor(null);

    }
    //діалогове вікно Додати новий урок
    private void openDialogAddNewLessons(){
        getLoaderManager().initLoader(SUBJECT_LOADER,null,this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            //вікно для додавання нового уроку
        builder.setTitle(R.string.add_lsn_title);
        LayoutInflater li = LayoutInflater.from(getActivity());
        View addNewLesson = li.inflate(R.layout.activity_add_new_lesson,null);
        AutoCompleteTextView subject = addNewLesson.findViewById(R.id.new_lesson_name);
        subject.setAdapter(subjectListAdapter);
        builder.setView(addNewLesson);
        builder.setCancelable(false)
               .setPositiveButton(R.string.btn_okey, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
               }).setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
               });
        builder.create().show();
    }
}

