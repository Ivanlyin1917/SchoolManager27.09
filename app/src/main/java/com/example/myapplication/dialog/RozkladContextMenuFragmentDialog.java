package com.example.myapplication.dialog;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.fragments.rozklad.RozkladSharedViewModel;

public class RozkladContextMenuFragmentDialog extends DialogFragment implements View.OnClickListener {
    private RozkladSharedViewModel model;
    private Uri lessonUri;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(RozkladSharedViewModel.class);
        lessonUri = model.getLessonUri();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contextMenu = inflater.inflate(R.layout.rozklad_context_menu_dialog,null);
        contextMenu.findViewById(R.id.tv_add_homework_to_lesson).setOnClickListener(this);
        contextMenu.findViewById(R.id.tv_edit_lesson).setOnClickListener(this);
        contextMenu.findViewById(R.id.tv_delete_lesson).setOnClickListener(this);
        return contextMenu;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            //дії для кнопки Додати ДЗ
            case R.id.tv_add_homework_to_lesson:

                break;
            // дії для кнопки Редагувати урок
            case R.id.tv_edit_lesson:
                editLesson();
                dismiss();
                break;
            // дії для кнопки Вилучити урок
            case R.id.tv_delete_lesson:
                deleteLesson();
                dismiss();
                break;
            default:break;
        }

    }
    private void deleteLesson(){
        ContentResolver cr = getActivity().getContentResolver();
        int countDelRec = cr.delete(lessonUri,null,null);
        if(countDelRec==0){
            Toast.makeText(getContext(), "Помилка! Не можу вилучити урок!", Toast.LENGTH_LONG).show();
        }
    }

    private void addHomework(){}

    private void editLesson(){
        AddLessonFragmentDialog addLessonFragmentDialog = new AddLessonFragmentDialog();
        addLessonFragmentDialog.show(getFragmentManager(),"editLsn");
    }
}
