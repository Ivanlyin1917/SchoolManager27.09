package com.example.myapplication.fragments.note;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.myapplication.Model.Note;
import com.example.myapplication.R;
import com.example.myapplication.adapter.NotesAdapter;
import com.example.myapplication.adapter.RozkladCursorAdapter;
import com.example.myapplication.data.SchoolManagerContract.*;

import java.util.ArrayList;
import java.util.List;

public class FragmentNote extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private final String TAG = "Note";
    private static final int NOTE_LOADER = 400;
    private NotesAdapter mAdapter;
    private List<Note> notesList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noNotesView;
    private int countRec;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getLoaderManager().initLoader(NOTE_LOADER,null,this);
        View noteFragment = inflater.inflate(R.layout.fragment_note, container, false);
        //Toolbar toolbar = noteFragment.findViewById(R.id.toolbar);
        //noteFragment.setSupportActionBar(toolbar);

        coordinatorLayout = noteFragment.findViewById(R.id.coordinator_layout);
        recyclerView = noteFragment.findViewById(R.id.recycler_view);
        noNotesView = noteFragment.findViewById(R.id.empty_notes_view);

        FloatingActionButton fab = (FloatingActionButton) noteFragment.findViewById(R.id.fab_note);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoteDialog(false, null, -1);
            }
        });

        /**
         * При довгому натисканні по запису відкриваємо
         * діалог з пунктами додати та редагувати
         * */
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
            }

            @Override
            public void onLongClick(View view, int position) {

                showActionsDialog(position);
            }
        }));

        initializationRecycler();
        return noteFragment;
    }
    /**
     * Додаємо новий запис до Бд
     * та оновлюємо лист
     */
    private void createNote(String note) {
        ContentValues cv = new ContentValues();
        ContentResolver contentResolver = getActivity().getContentResolver();
        cv.put(NoteEntry.KEY_NOTE,note);
        Uri noteInsertUri = contentResolver.insert(NoteEntry.NOTE_URI, cv);
        if (noteInsertUri == null) {
            Toast.makeText(getContext(), "Помилка! Не можу зберегти нотатку :(", Toast.LENGTH_LONG).show();
        }
        toggleEmptyNotes();
        /*Todo: можливо потрібно перегрузити курсор чи ще якимось чином оновити список */

       /* // get the newly inserted note from db
        Note n = db.getNote(id);

        if (n != null) {
            // adding new note to array list at 0 position
            notesList.add(0, n);

            // refreshing the list
            mAdapter.notifyDataSetChanged();

            toggleEmptyNotes();
        }*/
    }

    /**
     *Оновлюємо нотатку в БД
     * та оновлюємо список нотаток
     */
    private void updateNote(String note, int position) {
        int id = (notesList.get(position)).getNoteId();
        Uri noteUri = ContentUris.withAppendedId(NoteEntry.NOTE_URI,id );
        ContentValues cv = new ContentValues();
        ContentResolver contentResolver = getActivity().getContentResolver();
        cv.put(NoteEntry.KEY_NOTE,note);
        Log.i(TAG, "begin update note from position = "+position +" and id ="+id);
        int noteUpdateCount = contentResolver.update(noteUri, cv, null, null);
        Log.i(TAG, "Update "+noteUpdateCount+" note") ;
        if (noteUpdateCount == 0) {
            Toast.makeText(getContext(), "Помилка! Не можу оновити нотатку :(", Toast.LENGTH_LONG).show();
        }
        toggleEmptyNotes();

        /*// updating note in db
        db.updateNote(n);

        // refreshing the list
        notesList.set(position, n);
        mAdapter.notifyItemChanged(position);

        toggleEmptyNotes();*/
    }

    /**
     * Вилучаю нотатку з БД
     */
    private void deleteNote(int position) {
        int id = (notesList.get(position)).getNoteId();
        Uri noteUri = ContentUris.withAppendedId(NoteEntry.NOTE_URI,id);
        ContentResolver cr = getActivity().getContentResolver();
        Log.i(TAG, "begin delete note from position = "+position+" and id ="+id);
        int countDelRec = cr.delete(noteUri,null,null);
        Log.i(TAG, "delete "+countDelRec+" note") ;
        if(countDelRec==0){
            Toast.makeText(getContext(), "Помилка! Не можу вилучити нотатку!", Toast.LENGTH_LONG).show();
        }
        toggleEmptyNotes();
        mAdapter.notifyItemRemoved(position);

       /* // removing the note from the list
        notesList.remove(position);
        mAdapter.notifyItemRemoved(position);

        toggleEmptyNotes();*/
    }

    /**
     * Відкриваємо діалог з пп. Редагувати - Вилучити
     */
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Редагувати", "Вилучити"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Оберіть дію");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showNoteDialog(true, notesList.get(position), position);
                } else {
                    deleteNote(position);
                }
            }
        });
        builder.show();
    }

    /**
     * Показуємо діалог для введення тексту запису
     * під час створення або редагування нотатки
     * якщо shouldUpdate=true, автоматично відображається стара нотатка
     * і змінюється напис на кнопці UPDATE
     */
    private void showNoteDialog(final boolean shouldUpdate, final Note note, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity().getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.note_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
        alertDialogBuilderUserInput.setView(view);

        final EditText inputNote = view.findViewById(R.id.note);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_note_title) : getString(R.string.lbl_edit_note_title));

        if (shouldUpdate && note != null) {
            inputNote.setText(note.getNoteText());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "Оновити" : "Зберегти", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("Відмінити",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast message when no text is entered
                if (TextUtils.isEmpty(inputNote.getText().toString())) {
                    Toast.makeText(getContext(), "Enter note!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // check if user updating note
                if (shouldUpdate && note != null) {
                    // update note by it's id
                    updateNote(inputNote.getText().toString(), position);
                } else {
                    // create new note
                    createNote(inputNote.getText().toString());
                }
            }
        });
    }

    /**
     * Toggling list and empty notes view
     */
    private void toggleEmptyNotes() {
        // you can check notesList.size() > 0

        if (countRec > 0) {
            noNotesView.setVisibility(View.GONE);
        } else {
            noNotesView.setVisibility(View.VISIBLE);
        }
    }

    private void initializationRecycler(){
        mAdapter = new NotesAdapter(getContext(), notesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);
        toggleEmptyNotes();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        CursorLoader cursorLoader = new CursorLoader(getContext(),
                NoteEntry.NOTE_URI,null,null, null,null);
        Log.i(TAG, "select all note");
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()){
            notesList.clear();
            do {
                Note note = new Note();
                note.setNoteId(cursor.getInt(cursor.getColumnIndexOrThrow(NoteEntry.KEY_ID)));
                note.setNoteText(cursor.getString(cursor.getColumnIndexOrThrow(NoteEntry.KEY_NOTE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndexOrThrow(NoteEntry.KEY_TIMESTAMP)));
                notesList.add(note);
            }while (cursor.moveToNext());
        }
        countRec = cursor.getCount();
        mAdapter.notifyDataSetChanged();
        toggleEmptyNotes();

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
