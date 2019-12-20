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
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.fragments.homework.HomeworkSharedViewModel;


public class HomeworkContextMenuFragmentDialog extends DialogFragment implements View.OnClickListener {
    private HomeworkSharedViewModel model;
    private Uri homeworkUri;


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
       // contextMenu.findViewById(R.id.tv_add_homework_to_lesson).setOnClickListener(this);
        contextMenu.findViewById(R.id.tv_edit_homework).setOnClickListener(this);
        contextMenu.findViewById(R.id.tv_delete_homework).setOnClickListener(this);
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
            case R.id.tv_edit_homework:
                editHomework();
                dismiss();
                break;
            // дії для кнопки Вилучити урок
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

    private void addHomework(){}

    private void editHomework(){
        AddHomeworkFragmentDialog addHomeworkFragmentDialog = new AddHomeworkFragmentDialog();
        addHomeworkFragmentDialog.show(getFragmentManager(),"editHW");
    }
}
