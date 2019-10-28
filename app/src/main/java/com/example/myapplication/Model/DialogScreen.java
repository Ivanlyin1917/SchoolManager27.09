package com.example.myapplication.Model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.example.myapplication.R;

public class DialogScreen {
    //ідентифікатори діалогових вікон
    public static final int IDD_ADD_LESSON = 1;

    public static AlertDialog getDialog (Activity activity, int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        switch (id){
            case IDD_ADD_LESSON: //вікно для додавання нового уроку
            builder.setTitle(R.string.add_lsn_title);
                LayoutInflater li = LayoutInflater.from(activity);
                View addNewLesson = li.inflate(R.layout.activity_add_new_lesson,null);
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
            return builder.create();
            default: return null;
        }

    }
}
