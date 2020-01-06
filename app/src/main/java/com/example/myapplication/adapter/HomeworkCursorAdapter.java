package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import com.example.myapplication.data.SchoolManagerContract;
import com.example.myapplication.fragments.homework.PhotoActivity;

public class HomeworkCursorAdapter extends CursorAdapter {
    public HomeworkCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.homeworks_view_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView hwPhoto = view.findViewById(R.id.homework_photo);
        TextView lessonName = view.findViewById(R.id.homework_lesson_name_text);
        TextView homework = view.findViewById(R.id.homework_text);

        String photo = cursor.getString(cursor.getColumnIndexOrThrow(SchoolManagerContract.HomeworksEntry.HW_PHOTO));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(SchoolManagerContract.SubjectEntry.KEY_NAME));
        String hw = cursor.getString(cursor.getColumnIndexOrThrow(SchoolManagerContract.HomeworksEntry.HOMEWORK));
        int isReady = cursor.getInt(cursor.getColumnIndexOrThrow(SchoolManagerContract.HomeworksEntry.HW_IS_READY));
        if (isReady==1){
            SpannableString newName = new SpannableString(name);
            newName.setSpan(new StrikethroughSpan(),0,newName.length(),0);
            SpannableString newHw = new SpannableString(hw);
            newHw.setSpan(new StrikethroughSpan(),0,newHw.length(),0);
            lessonName.setText(newName);
            homework.setText(newHw);
        }else{
            lessonName.setText(name);
            homework.setText(hw);
        }

        //Декодируем String в изображение
        if(photo!=null && !photo.equals("")){
            byte []decodedString = Base64.decode(photo,Base64.URL_SAFE);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            hwPhoto.setImageBitmap(decodedByte);
            hwPhoto.setClickable(true);
            hwPhoto.setEnabled(true);
            hwPhoto.setVisibility(View.VISIBLE);
        } else {
            //Bitmap newBitmap = BitmapFactory.decodeByteArray(new byte[1],0,1);
            hwPhoto.setEnabled(false);
            hwPhoto.setVisibility(View.INVISIBLE);
        }
        //при нажатии на картинку показываем ее в помном размере
        hwPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoIntent = new Intent(context, PhotoActivity.class);
                photoIntent.putExtra("photo", photo);
                context.startActivity(photoIntent);
            }
        });
        /*TODO проблема появляется фото при его отсутствии*/


    }
}
