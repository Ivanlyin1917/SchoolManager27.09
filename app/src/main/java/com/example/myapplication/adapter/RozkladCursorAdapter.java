package com.example.myapplication.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.data.SchoolManagerContract;

public class RozkladCursorAdapter extends CursorAdapter {

    public RozkladCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.rozklad_view_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView itemNumber = view.findViewById(R.id.rozklad_item_number_text);
       /* TextView lessonTime = view.findViewById(R.id.rozklad_time_text);*/
        TextView lessonName = view.findViewById(R.id.rozklad_lesson_name_text);
        TextView lessonPlace = view.findViewById(R.id.rozklad_place_text);

        String number = "";
       /* String time = cursor.getString(cursor.getColumnIndexOrThrow(SchoolManagerContract.JingleEntry.TIME_BEGIN))+"-"
                +cursor.getString(cursor.getColumnIndexOrThrow(SchoolManagerContract.JingleEntry.TIME_END  ));*/
        String name = cursor.getString(cursor.getColumnIndexOrThrow(SchoolManagerContract.SubjectEntry.KEY_NAME));
        String place = cursor.getString(cursor.getColumnIndexOrThrow(SchoolManagerContract.LessonsEntry.LESSON_PLACE));
        itemNumber.setText(number);
        /*lessonTime.setText(time);*/
        lessonName.setText(name);
        lessonPlace.setText(place);

    }
}
