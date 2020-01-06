package com.example.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Model.Note;
import com.example.myapplication.Model.Teacher;
import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.MyViewHolder> {
    private Context context;
    private List<Teacher> teachersList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView teacher;
        public TextView dot;
        public TextView subject;

        public MyViewHolder(View view) {
            super(view);
            teacher = view.findViewById(R.id.note);
            dot = view.findViewById(R.id.dot);
            subject = view.findViewById(R.id.timestamp);
        }
    }


    public TeacherAdapter(Context context, List<Teacher> teachersList ) {
        this.context = context;
        this.teachersList = teachersList;

    }

    @Override
    public TeacherAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        return new TeacherAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TeacherAdapter.MyViewHolder holder, int position) {
        Teacher teacher = teachersList.get(position);
        String fio = teacher.getSurname()+" "+teacher.getName()+" "+teacher.getLastName();

        holder.teacher.setText(fio);


        // Отображение точки из кода символа HTML - маркер
        holder.dot.setText(Html.fromHtml("&#8226;"));

        // Форматирование и отображение метки времени
        holder.subject.setText(teacher.getSubject());

    }

    @Override
    public int getItemCount() {
        return teachersList.size();
        //return cursor.getCount();
    }


}
