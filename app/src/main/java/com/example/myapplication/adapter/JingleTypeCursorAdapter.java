package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.data.SchoolManagerContract.*;
import com.example.myapplication.fragments.jingle.FragmentJingle;

public class JingleTypeCursorAdapter extends CursorAdapter  {

    public JingleTypeCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.jingle_type_row,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView jingleType = view.findViewById(R.id.jingle_type_name);
        String typeText = cursor.getString(cursor.getColumnIndexOrThrow(JingleTypeEntry.TYPE_NAME));
        //int typeId =  cursor.getInt(cursor.getColumnIndexOrThrow(JingleTypeEntry.JINGLE_TYPE_ID));
        jingleType.setText(typeText);
        //TextView dot = view.findViewById(R.id.jingle_dot);
       // dot.setText(Html.fromHtml("&#8226;"));


    }


}
