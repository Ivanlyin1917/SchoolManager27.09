package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;

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

        Button jingleTypeBtn = view.findViewById(R.id.jingle_type_btn);
        jingleTypeBtn.setCompoundDrawablesWithIntrinsicBounds(0,0,
                R.drawable.ic_expand_more_red_24dp,0);
        LinearLayout jingleLayout = view.findViewById(R.id.jingle_layout);
        String btnText = cursor.getString(cursor.getColumnIndexOrThrow(JingleTypeEntry.TYPE_NAME));
        int typeId =  cursor.getInt(cursor.getColumnIndexOrThrow(JingleTypeEntry.JINGLE_TYPE_ID));
        jingleTypeBtn.setText(btnText);

                jingleTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (jingleLayout.getVisibility() == View.VISIBLE) {

                    jingleLayout.setVisibility(View.GONE);
                    jingleTypeBtn.setCompoundDrawablesWithIntrinsicBounds(0,0,
                            R.drawable.ic_expand_more_red_24dp,0);
                } else {
                    FragmentJingle fragment = FragmentJingle.newInstance(typeId);

                  //  jingleLayout.addView(fragment);
                    jingleLayout.setVisibility(View.VISIBLE);
                    jingleTypeBtn.setCompoundDrawablesWithIntrinsicBounds(0,0,
                            R.drawable.ic_expand_less_red_24dp,0);
                }
                ScaleAnimation animation = new ScaleAnimation(1f, 1f, 1f, 0f,
                        Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
                animation.setDuration(180);
                animation.setFillAfter(true);
                jingleLayout.setAnimation(animation);

            }
        });

    }


}
