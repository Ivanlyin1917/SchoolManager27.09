package com.example.myapplication.dialog;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.Model.Homeworks;
import com.example.myapplication.R;
import com.example.myapplication.adapter.HomeworkCursorAdapter;
import com.example.myapplication.fragments.homework.HomeworkSharedViewModel;
import com.example.myapplication.data.SchoolManagerContract.*;


public class HomeworkContextMenuFragmentDialog extends DialogFragment implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final int HOMEWORK_LOADER = 200;
    private HomeworkCursorAdapter hwCursorAdapter;
    private HomeworkSharedViewModel model;
    private char idAction;
    private Uri homeworkUri;
    private Homeworks hw;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(HomeworkSharedViewModel.class);
        homeworkUri = model.getHomework_uri();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contextMenu = inflater.inflate(R.layout.homework_context_menu_dialog,null);
        contextMenu.findViewById(R.id.tv_done_homework).setOnClickListener(this);
        contextMenu.findViewById(R.id.tv_clear_photo_hw).setOnClickListener(this);
        contextMenu.findViewById(R.id.tv_edit_homework).setOnClickListener(this);
        contextMenu.findViewById(R.id.tv_delete_homework).setOnClickListener(this);
        return contextMenu;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            //дії для кнопки Готово
            case R.id.tv_done_homework:
                hwCursorAdapter = new HomeworkCursorAdapter(getContext(),null,false);
                getLoaderManager().initLoader(HOMEWORK_LOADER, null,this);
                idAction='r';
                dismiss();
                break;
            //дії для кнопки Вилучити фото
            case R.id.tv_clear_photo_hw:
                hwCursorAdapter = new HomeworkCursorAdapter(getContext(),null,false);
                getLoaderManager().initLoader(HOMEWORK_LOADER, null,this);
                idAction='p';
                dismiss();
                break;
            // дії для кнопки Редагувати ДЗ
            case R.id.tv_edit_homework:
                editHomework();
                dismiss();
                break;
            // дії для кнопки Вилучити ДЗ
            case R.id.tv_delete_homework:
                deleteHomework();
                dismiss();
                break;
            default:break;
        }

    }
    private void deleteHomework(){
        ContentResolver cr = getActivity().getContentResolver();
        int countDelRec = cr.delete(homeworkUri,null,null);
        if(countDelRec==0){
            Toast.makeText(getContext(), "Помилка! Не можу вилучити ДЗ!", Toast.LENGTH_LONG).show();
        }
    }

    private void changeHomework(){
        ContentValues cv = new ContentValues();
        ContentResolver contentResolver = getActivity().getContentResolver();
        cv.put(HomeworksEntry.DATE_HW,hw.getDate_hm());
        cv.put(LessonsEntry.SUBJECT_ID, hw.getSubject_id());
        cv.put(HomeworksEntry.HOMEWORK, hw.getHomework());
        switch (idAction){
            case 'r':
                cv.put(HomeworksEntry.HW_PHOTO,hw.getPhoto_hw());
                cv.put(HomeworksEntry.HW_IS_READY,1);
                break;
            case 'p':
                cv.put(HomeworksEntry.HW_PHOTO,"");
                cv.put(HomeworksEntry.HW_IS_READY,hw.isHwIsReady());
                break;
        }
        int hwUpdateCount = contentResolver.update(homeworkUri, cv, null, null);
        if (hwUpdateCount == 0) {
            Toast.makeText(getContext(), "Помилка! Не можу оновити дані :(", Toast.LENGTH_LONG).show();
        }
    }

    private void editHomework(){
        AddHomeworkFragmentDialog addHomeworkFragmentDialog = new AddHomeworkFragmentDialog();
        addHomeworkFragmentDialog.show(getFragmentManager(),"editHW");
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        CursorLoader cursorLoaderHomework = new CursorLoader(getContext(),
                homeworkUri,null,null,null,null);
        return cursorLoaderHomework;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        hwCursorAdapter.swapCursor(cursor);
        if (cursor.moveToFirst()){
            hw = new Homeworks();
            do {
                hw.setHm_id(cursor.getInt(cursor.getColumnIndexOrThrow(HomeworksEntry.HM_ID)));
                hw.setSubject_id(cursor.getInt(cursor.getColumnIndexOrThrow(LessonsEntry.SUBJECT_ID)));
                hw.setSubject_name(cursor.getString(cursor.getColumnIndexOrThrow(SubjectEntry.KEY_NAME)));
                hw.setHomework(cursor.getString(cursor.getColumnIndexOrThrow(HomeworksEntry.HOMEWORK)));
                hw.setDate_hm(cursor.getString(cursor.getColumnIndexOrThrow(HomeworksEntry.DATE_HW)));
                hw.setPhoto_hw(cursor.getString(cursor.getColumnIndexOrThrow(HomeworksEntry.HW_PHOTO)));
                hw.setHwIsReady(cursor.getInt(cursor.getColumnIndexOrThrow(HomeworksEntry.HW_IS_READY)));
            }while (cursor.moveToNext());
            changeHomework();
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        hwCursorAdapter.swapCursor(null);

    }
}
