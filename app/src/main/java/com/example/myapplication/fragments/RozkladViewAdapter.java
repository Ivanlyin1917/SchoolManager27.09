package com.example.myapplication.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

public class RozkladViewAdapter extends ArrayAdapter<RozkladViewItem> {

    private LayoutInflater inflater;
    private int layout;
    private List<RozkladViewItem> listLessons;

    public RozkladViewAdapter( Context context, int resource, List <RozkladViewItem> listLessons) {
        super(context, resource, listLessons);
        this.listLessons = listLessons;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView itemNumber = view.findViewById(R.id.rozklad_item_number_text);
        TextView lessonTime = view.findViewById(R.id.rozklad_time_text);
        TextView lessonName = view.findViewById(R.id.rozklad_lesson_name_text);
        TextView lessonPlace = view.findViewById(R.id.rozklad_place_text);

        RozkladViewItem rozkladItem = listLessons.get(position);

        itemNumber.setText(rozkladItem.getItemNumber());
        lessonTime.setText(rozkladItem.getLessonTime());
        lessonName.setText(rozkladItem.getLessonName());
        lessonPlace.setText(rozkladItem.getLessonPlace());

        return view;
    }
}
