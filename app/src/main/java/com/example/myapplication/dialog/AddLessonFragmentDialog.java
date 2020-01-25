package com.example.myapplication.dialog;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



import com.example.myapplication.R;
import com.example.myapplication.adapter.RozkladCursorAdapter;
import com.example.myapplication.adapter.SubjectCursorAdapter;
import com.example.myapplication.data.SchoolManagerContract.*;
import com.example.myapplication.fragments.rozklad.RozkladSharedViewModel;
import com.example.myapplication.fragments.rozklad.RozkladViewItem;

import java.net.URI;


public class AddLessonFragmentDialog extends DialogFragment implements View.OnClickListener,
        TextWatcher,LoaderManager.LoaderCallbacks<Cursor>{

    private static final int SUBJECT_LOADER = 300;
    private static final int ROZKLAD_LOADER = 200;
    private static final String TAG = "Rozklad";
    private AutoCompleteTextView userInput;
    private SubjectCursorAdapter subjectCursorAdapter;
    private RozkladCursorAdapter rozkladCursorAdapter;
    private  RozkladViewItem lesson;
    private RozkladSharedViewModel model;
    private Spinner spinner;
    private CheckBox isEmpty;
    private EditText lsnRoom;
    long lsnId=-1;
    String newLsnName;
    Uri lessonUri;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(RozkladSharedViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View addLessonView = inflater.inflate(R.layout.activity_add_new_lesson, null);
        lsnRoom = addLessonView.findViewById(R.id.new_number_room);
        spinner = addLessonView.findViewById(R.id.spenner_number_lesson);
        isEmpty = addLessonView.findViewById(R.id.cb_is_empty);
        userInput = addLessonView.findViewById(R.id.new_lesson_name);
        TextView addBtn = addLessonView.findViewById(R.id.btn_add);
        TextView titleDlg = addLessonView.findViewById(R.id.tv_title_add_lsn_dialog);

        //встановлюю onClick на кнопки
        addLessonView.findViewById(R.id.btn_add).setOnClickListener(this);
        addLessonView.findViewById(R.id.btn_cancel).setOnClickListener(this);
       //ініціалізую список значень в AutoCompleteTextView
        lessonUri = model.getLessonUri();
        initialAutoCompleteTextView();
        Log.i(TAG, "get uri="+lessonUri);
        if (lessonUri==null){
            addBtn.setText(R.string.btn_add);
            titleDlg.setText(R.string.add_lsn_title);
            spinner.setSelected(true);
        }else{
            titleDlg.setText(R.string.edit_lsn_title);
            addBtn.setText(R.string.btn_okey);
            TextView tv = addLessonView.findViewById(R.id.tv_text_count);
            tv.setVisibility(View.INVISIBLE);
            spinner.setVisibility(View.INVISIBLE);
        }
        return addLessonView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            //дії для кнопки Додати
            case R.id.btn_add:
                insertNewLesson();
                break;
            // дії для кнопки Відмінити
            case R.id.btn_cancel: dismiss();
                break;
            default:break;
        }
    }

    private void initialAutoCompleteTextView (){
        userInput.addTextChangedListener(this);//для фільтрації автовибору
        subjectCursorAdapter = new SubjectCursorAdapter(getContext(), null, false);
         Bundle bundle = new Bundle();
        bundle.putString("arg","%");
        getLoaderManager().initLoader(SUBJECT_LOADER,bundle,this);
        userInput.setAdapter(subjectCursorAdapter);
        //id вибраного уроку
        userInput.setOnItemClickListener(new AdapterView.OnItemClickListener() { //для отримання вибору
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               lsnId=id;
            }
        });

        if (lessonUri !=null){

            rozkladCursorAdapter = new RozkladCursorAdapter(getContext(),null,false);
            getLoaderManager().initLoader(ROZKLAD_LOADER, null,this);
            lesson = new RozkladViewItem();
        }
    }


    private void insertNewLesson(){
        ContentValues cv = new ContentValues();
        cv.put(LessonsEntry.DAY_ID,  model.getWeekDay());
        Log.i(TAG,"working with day="+ model.getWeekDay());
        int countLsn = 1;
        if (isEmpty.isChecked()){
            cv.put(LessonsEntry.SUBJECT_ID,0);
        }else{
            if(lsnId==-1){
                //предмета немає в БД. Спочатку збережемо його і отримаємо його id
                lsnId=insertNewSubject();
            }
            countLsn = Integer.parseInt(spinner.getSelectedItem().toString());//к-сть уроків
            String lRoom = lsnRoom.getText().toString();//кабінет
            cv.put(LessonsEntry.SUBJECT_ID,lsnId);
            cv.put(LessonsEntry.LESSON_PLACE,lRoom);
        }
        ContentResolver contentResolver = getActivity().getContentResolver();
        if (lessonUri==null){

            for(int i=1; i<=countLsn; i++){
                Uri lsnInsertUri = contentResolver.insert(LessonsEntry.ROZKLAD_URI,cv);
                if(lsnInsertUri==null){
                    Toast.makeText(getContext(), "Помилка збереження даних", Toast.LENGTH_LONG).show();
                }
            }
        }else{
            int lsnUpdateCount = contentResolver.update(lessonUri,cv,null,null);
            if(lsnUpdateCount==0){
                Toast.makeText(getContext(), "Помилка! Не можу оновити дані :(", Toast.LENGTH_LONG).show();
            }
        }

        dismiss();
    }
    private long insertNewSubject(){
        ContentValues scv = new ContentValues();
        scv.put(SubjectEntry.KEY_NAME,newLsnName);
        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri uri = contentResolver.insert(SubjectEntry.SUBJECT_URI,scv);
        return ContentUris.parseId(uri);
    }



    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        switch (i){
            case SUBJECT_LOADER:
                String[] projection = new String[]{SubjectEntry.KEY_ID, SubjectEntry.KEY_NAME};
                String selection=SubjectEntry.KEY_NAME+" like ?";
                String [] arg = new String[]{bundle.getString("arg")};

                CursorLoader cursorLoaderSubject = new CursorLoader(getContext(),
                        SubjectEntry.SUBJECT_URI,projection,selection,arg,null);
                return cursorLoaderSubject;
            case ROZKLAD_LOADER:
                CursorLoader cursorLoaderRozklad = new CursorLoader(getContext(),
                        lessonUri,null,null,null,null);
                return cursorLoaderRozklad;
            default: return null;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        int id = loader.getId();
        switch (id){
            case SUBJECT_LOADER:
                subjectCursorAdapter.swapCursor(cursor);
                break;
            case ROZKLAD_LOADER:
                rozkladCursorAdapter.swapCursor(cursor);
                if (cursor.moveToFirst()){
                    lesson = new RozkladViewItem();
                    do {
                        lesson.setId(cursor.getInt(cursor.getColumnIndexOrThrow(LessonsEntry.LESSON_ID)));
                        lesson.setSubjectId(cursor.getInt(cursor.getColumnIndexOrThrow(LessonsEntry.SUBJECT_ID)));
                        lesson.setLessonName(cursor.getString(cursor.getColumnIndexOrThrow(SubjectEntry.KEY_NAME)));
                        lesson.setLessonPlace(cursor.getString(cursor.getColumnIndexOrThrow(LessonsEntry.LESSON_PLACE)));
                    }while (cursor.moveToNext());
                }
                userInput.setText(lesson.getLessonName());
                lsnRoom.setText(lesson.getLessonPlace());
                lsnId = lesson.getId();
                break;
            default:break;
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        int id = loader.getId();
        switch (id) {
            case SUBJECT_LOADER:
                subjectCursorAdapter.swapCursor(null);
                break;
            case ROZKLAD_LOADER:
                rozkladCursorAdapter.swapCursor(null);
                break;
            default:break;
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
        newLsnName=s.toString();

    }
}
