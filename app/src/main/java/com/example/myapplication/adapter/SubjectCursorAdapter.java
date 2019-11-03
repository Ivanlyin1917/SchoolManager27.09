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

public class SubjectCursorAdapter extends CursorAdapter {
    public SubjectCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_dropdown_item_1line,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView subjectName = view.findViewById(android.R.id.text1);
        String name = cursor.getString(cursor.getColumnIndexOrThrow(SchoolManagerContract.SubjectEntry.KEY_NAME));
        subjectName.setText(name);

    }

    @Override
    public CharSequence convertToString(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(SchoolManagerContract.SubjectEntry.KEY_NAME));
    }
}
