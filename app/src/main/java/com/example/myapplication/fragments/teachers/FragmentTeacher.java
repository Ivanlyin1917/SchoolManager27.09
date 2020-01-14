package com.example.myapplication.fragments.teachers;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.Teacher;
import com.example.myapplication.R;

import com.example.myapplication.adapter.SubjectCursorAdapter;
import com.example.myapplication.adapter.TeacherAdapter;
import com.example.myapplication.data.SchoolManagerContract.*;
import com.example.myapplication.fragments.note.MyDividerItemDecoration;
import com.example.myapplication.fragments.note.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentTeacher extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        View.OnClickListener, TextWatcher {
    private final String TAG = "Teacher";
    private static final int TEACHER_LOADER = 500;
    private static final int SUBJECT_LOADER = 300;
    private TeacherAdapter mAdapter;
    private List<Teacher> teacherList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noNotesView;
    //private AutoCompleteTextView subjectTV;
    private SubjectCursorAdapter subjectCursorAdapter;
    private int countRec;
    private long subject_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getLoaderManager().initLoader(TEACHER_LOADER,null,this);
        View teacherFragment = inflater.inflate(R.layout.fragment_teacher, container, false);


        coordinatorLayout = teacherFragment.findViewById(R.id.coordinator_layout);
        recyclerView = teacherFragment.findViewById(R.id.recycler_view);
        noNotesView = teacherFragment.findViewById(R.id.empty_teacher_view);
        //subjectTV = teacherFragment.findViewById(R.id.teacher_subject);


        FloatingActionButton fab = teacherFragment.findViewById(R.id.fab_teacher);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTeacherDialog(false, null, -1);
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
       // initialAutoCompleteTextView();
        return teacherFragment;
    }
    /**
     * Додаємо новий запис до Бд
     * та оновлюємо лист
     */
    private void createTeacher(String surname, String name, String lastname, long subject_id) {
        ContentValues cv = new ContentValues();
        ContentResolver contentResolver = getActivity().getContentResolver();
        cv.put(TeachersEntry.SURNAME,surname);
        cv.put(TeachersEntry.NAME,name);
        cv.put(TeachersEntry.LASTNAME,lastname);
        Uri teacherInsertUri = contentResolver.insert(TeachersEntry.TEACHER_URI, cv);
        if (teacherInsertUri == null) {
            Toast.makeText(getContext(), "Помилка! Не можу зберегти дані про вчителя :(", Toast.LENGTH_LONG).show();
        } else {
            long teacher_id = ContentUris.parseId(teacherInsertUri);
            if (subject_id!=0){
                ContentValues cvs = new ContentValues();
                cvs.put(TeacherSubjectEntry.SUBJECT_ID, subject_id);
                cvs.put(TeacherSubjectEntry.TEACHER_ID,teacher_id);
                Uri ts_uri = contentResolver.insert(TeacherSubjectEntry.TS_URI,cvs);
                if (ts_uri == null)
                    Toast.makeText(getContext(), "Помилка! Не можу зберегти зв'язок вчителя з предметом", Toast.LENGTH_LONG).show();
            }
        }
        toggleEmptyNotes();
    }

    /**
     *Оновлюємо нотатку в БД
     * та оновлюємо список нотаток
     */
    private void updateTeacher(String surname, String name, String lastname, int position, long subject_id) {
        long id = (teacherList.get(position)).getTeacher_id();
        Uri teacherUri = ContentUris.withAppendedId(TeachersEntry.TEACHER_URI,id );
        ContentValues cv = new ContentValues();
        ContentResolver contentResolver = getActivity().getContentResolver();
        cv.put(TeachersEntry.SURNAME,surname);
        cv.put(TeachersEntry.NAME,name);
        cv.put(TeachersEntry.LASTNAME,lastname);
        Log.i(TAG, "begin update teacher from position = "+position +" and id ="+id);
        int teacherUpdateCount = contentResolver.update(teacherUri, cv, null, null);
        Log.i(TAG, "Update "+teacherUpdateCount+" note") ;
        if (teacherUpdateCount == 0) {
            Toast.makeText(getContext(), "Помилка! Не можу оновити запис про вчителя :(", Toast.LENGTH_LONG).show();
        }
        toggleEmptyNotes();
        cv.clear();
        cv.put(TeacherSubjectEntry.TEACHER_ID, id);
        long ts_id = (teacherList.get(position)).getTs_id();
        Uri tsUri = ContentUris.withAppendedId(TeacherSubjectEntry.TS_URI,ts_id);
        if (subject_id==0){
            if (ts_id!=0) contentResolver.delete(tsUri,null,null);
        }else{
            cv.put(TeacherSubjectEntry.SUBJECT_ID,subject_id);
            if (ts_id == 0){
                Uri newTsUri = contentResolver.insert(TeacherSubjectEntry.TS_URI,cv);
                if (newTsUri == null)
                    Toast.makeText(getContext(), "Помилка! Не можу зберегти зв'язок вчителя з предметом", Toast.LENGTH_LONG).show();
            }else{
                Log.i(TAG, "begin update subject for teacher from position = "+position +" and id ="+id);
                int tsCountUpdate = contentResolver.update(tsUri,cv,null,null);
                if (tsCountUpdate == 0) {
                    Toast.makeText(getContext(), "Помилка! Не можу оновити предмет, який викладає вчитель :(", Toast.LENGTH_LONG).show();
                }
            }

        }

    }

    /**
     * Вилучаю нотатку з БД
     */
   private void deleteTeacher(int position) {
        long id = (teacherList.get(position)).getTeacher_id();
        Uri teacherUri = ContentUris.withAppendedId(TeachersEntry.TEACHER_URI,id);
        long ts_id = (teacherList.get(position)).getTs_id();
        Uri tsUri = ContentUris.withAppendedId(TeacherSubjectEntry.TS_URI,ts_id);
        ContentResolver cr = getActivity().getContentResolver();
        if (ts_id!=0){
            Log.i(TAG, "begin delete subject for teacher from position = "+position +" and id ="+id);
            int countDeleteTs = cr.delete(tsUri,null,null);
            if(countDeleteTs==0){
                Toast.makeText(getContext(), "Помилка! Не можу вилучити предмет, який викладає вчитель!", Toast.LENGTH_LONG).show();
            }
        }
        Log.i(TAG, "begin delete teacher from position = "+position+" and id ="+id);
        int countDelRec = cr.delete(teacherUri,null,null);
        Log.i(TAG, "delete "+countDelRec+" Teacher") ;
        if(countDelRec==0){
            Toast.makeText(getContext(), "Помилка! Не можу вилучити запис про вчителя!", Toast.LENGTH_LONG).show();
        }
        toggleEmptyNotes();

   }

    /**
     * Відкриваємо діалог з пп. Редагувати - Вилучити
     */
    private void showActionsDialog(final int position) {

        CharSequence colors[] = new CharSequence[]{"Редагувати", "Вилучити"};
       // int subject_id = (teacherList.get(position)).getSubject_id();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Оберіть дію");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        showTeacherDialog(true, teacherList.get(position), position);
                        break;
                    case 1:
                        deleteTeacher(position);
                        break;
                    default:
                        Toast.makeText(getContext(), "Помилка! Невизначена дія! Спробуйте пізніше1", Toast.LENGTH_SHORT).show();
                        break;

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
    private void showTeacherDialog(final boolean shouldUpdate, final Teacher teacher, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity().getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.teacher_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
        alertDialogBuilderUserInput.setView(view);

        final EditText inputSurname = view.findViewById(R.id.teacher_surname);
        final EditText inputName = view.findViewById(R.id.teacher_name);
        final EditText inputLastname = view.findViewById(R.id.teacher_lastname);
        final AutoCompleteTextView subjectTV = view.findViewById(R.id.teacher_subject);

        TextView dialogTitle = view.findViewById(R.id.teacher_dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_teacher_title) : getString(R.string.lbl_edit_teacher_title));

        subjectTV.addTextChangedListener(this);//для фільтрації автовибору
        subjectCursorAdapter = new SubjectCursorAdapter(getContext(), null, false);
        Bundle bundle = new Bundle();
        bundle.putString("arg","%");
        getLoaderManager().initLoader(SUBJECT_LOADER,bundle,this);
        subjectTV.setAdapter(subjectCursorAdapter);

        subject_id = 0;
        //id вибраного уроку
        subjectTV.setOnItemClickListener(new AdapterView.OnItemClickListener() { //для отримання вибору
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subject_id = id;
            }
        });


        if (shouldUpdate && teacher != null) {
            inputSurname.setText(teacher.getSurname());
            inputName.setText(teacher.getName());
            inputLastname.setText(teacher.getLastName());
            if (teacher.getSubject_id()>0) {
                subjectTV.setText(teacher.getSubject());
                subject_id = teacher.getSubject_id();
            }
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
                if (TextUtils.isEmpty(inputSurname.getText().toString())||TextUtils.isEmpty(inputName.getText().toString())||TextUtils.isEmpty(inputLastname.getText().toString() )) {
                    Toast.makeText(getContext(), "Введіть ПІП вчителя", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // check if user updating note
                if (shouldUpdate && teacher != null) {
                    // update note by it's id
                    updateTeacher(inputSurname.getText().toString(),inputName.getText().toString(),inputLastname.getText().toString(), position,subject_id);
                } else {
                    // create new note
                    createTeacher(inputSurname.getText().toString(),inputName.getText().toString(),inputLastname.getText().toString(), subject_id);
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
        mAdapter = new TeacherAdapter(getContext(), teacherList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);
        toggleEmptyNotes();
    }

//    private void initialAutoCompleteTextView (){
//        subjectTV.addTextChangedListener(this);//для фільтрації автовибору
//        subjectCursorAdapter = new SubjectCursorAdapter(getContext(), null, false);
//        Bundle bundle = new Bundle();
//        bundle.putString("arg","%");
//        getLoaderManager().initLoader(SUBJECT_LOADER,bundle,this);
//        userInput.setAdapter(subjectCursorAdapter);
//        //id вибраного уроку
//        userInput.setOnItemClickListener(new AdapterView.OnItemClickListener() { //для отримання вибору
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                lsnId=id;
//            }
//        });

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {

        switch (i) {
            case SUBJECT_LOADER:
                String[] projection = new String[]{SubjectEntry.KEY_ID, SubjectEntry.KEY_NAME};
                String selection = SubjectEntry.KEY_NAME + " like ?";
                String[] arg = new String[]{bundle.getString("arg")};

                CursorLoader cursorLoaderSubject = new CursorLoader(getContext(),
                        SubjectEntry.SUBJECT_URI, projection, selection, arg, null);
                Log.i(TAG, "select subject which has \'" + arg.toString() + "\'");
                return cursorLoaderSubject;
            case TEACHER_LOADER:
                CursorLoader cursorLoader = new CursorLoader(getContext(),
                        TeachersEntry.TEACHER_URI, null, null, null, null);
                Log.i(TAG, "select all teacher");
                return cursorLoader;
                default: return null;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        int id = loader.getId();
        switch (id) {
            case SUBJECT_LOADER:
                subjectCursorAdapter.swapCursor(cursor);
                break;
            case TEACHER_LOADER:
                if (cursor.moveToFirst()){
                    teacherList.clear();
                    do {
                        Teacher teacher= new Teacher();
                        //teacher.setTeacher_id(cursor.getLong(cursor.getColumnIndexOrThrow(TeachersEntry.TEACHER_ID)));
                        teacher.setTeacher_id(cursor.getLong(0));
                        teacher.setSurname(cursor.getString(cursor.getColumnIndexOrThrow(TeachersEntry.SURNAME)));
                        teacher.setName(cursor.getString(cursor.getColumnIndexOrThrow(TeachersEntry.NAME)));
                        teacher.setLastName(cursor.getString(cursor.getColumnIndexOrThrow(TeachersEntry.LASTNAME)));
                        teacher.setSubject(cursor.getString(cursor.getColumnIndexOrThrow(SubjectEntry.KEY_NAME)));
                        //teacher.setSubject_id(cursor.getLong(cursor.getColumnIndexOrThrow(SubjectEntry.KEY_ID)));
                        teacher.setSubject_id(cursor.getLong(5));
                        //teacher.setTs_id(cursor.getLong(cursor.getColumnIndexOrThrow(TeacherSubjectEntry.TS_ID)));
                        teacher.setTs_id(cursor.getLong(6));
                        teacherList.add(teacher);
                    }while (cursor.moveToNext());
                }
                countRec = cursor.getCount();
                mAdapter.notifyDataSetChanged();
                toggleEmptyNotes();
                break;
            default:
                break;
        }


    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        int id = loader.getId();
        switch (id) {
            case SUBJECT_LOADER:
                subjectCursorAdapter.swapCursor(null);
                break;
            default:
                break;
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Bundle newBundle = new Bundle();
        newBundle.putString("arg","%"+s.toString()+"%");
        getLoaderManager().restartLoader(SUBJECT_LOADER, newBundle, this);
        subjectCursorAdapter.changeCursor(null);

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {

    }
}
