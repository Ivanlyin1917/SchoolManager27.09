package com.example.myapplication.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.data.SchoolManagerContract.*;

public class JingleCursorAdapter extends CursorAdapter {

    public JingleCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.jingle_row,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView pos = view.findViewById(R.id.jingle_position);
        TextView timeBgn = view.findViewById(R.id.jingle_bgn);
        TextView timeEnd = view.findViewById(R.id.jingle_end);

        String posText = cursor.getString(cursor.getColumnIndexOrThrow(JingleEntry.POSITION_ID))+".";
        String timeBgnText = cursor.getString(cursor.getColumnIndexOrThrow(JingleEntry.TIME_BEGIN));
        String timeEndText = cursor.getString(cursor.getColumnIndexOrThrow(JingleEntry.TIME_END));

        pos.setText(posText);
        timeBgn.setText(timeBgnText);
        timeEnd.setText(timeEndText);


    }
}
