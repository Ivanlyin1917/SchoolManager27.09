package com.example.myapplication.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Model.Note;
import com.example.myapplication.R;
import com.example.myapplication.data.SchoolManagerContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {
    private Context context;
    private List<Note> notesList;
    // private Cursor cursor;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView note;
        public TextView dot;
        public TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            note = view.findViewById(R.id.note);
            dot = view.findViewById(R.id.dot);
            timestamp = view.findViewById(R.id.timestamp);
        }
    }


    public NotesAdapter(Context context, List<Note> notesList /* Cursor c*/) {
        this.context = context;
        this.notesList = notesList;
        //this.cursor = cursor;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Note note = notesList.get(position);

        holder.note.setText(note.getNoteText());
        //String noteText = cursor.getString(cursor.getColumnIndexOrThrow(SchoolManagerContract.NoteEntry.KEY_NOTE));
        //holder.note.setText(noteText);

        // Отображение точки из кода символа HTML - маркер
        holder.dot.setText(Html.fromHtml("&#8226;"));

        // Форматирование и отображение метки времени
        holder.timestamp.setText(formatDate(note.getTimestamp()));
        //String date = cursor.getString(cursor.getColumnIndexOrThrow(SchoolManagerContract.NoteEntry.KEY_TIMESTAMP));
        //holder.timestamp.setText(formatDate(date));
    }

    @Override
    public int getItemCount() {
        return notesList.size();
        //return cursor.getCount();
    }

    /**
     * Форматирование метки времени в `MMM d` форомат
     * Ввод: 2018-02-21 00:15:42
     * Вывод: Feb 21
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }

}
