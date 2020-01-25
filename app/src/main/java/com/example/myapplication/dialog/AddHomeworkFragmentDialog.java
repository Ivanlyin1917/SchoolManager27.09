package com.example.myapplication.dialog;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.Homeworks;
import com.example.myapplication.R;
import com.example.myapplication.adapter.HomeworkCursorAdapter;
import com.example.myapplication.adapter.SubjectCursorAdapter;
import com.example.myapplication.data.SchoolManagerContract.*;
import com.example.myapplication.fragments.homework.HomeworkSharedViewModel;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

public class AddHomeworkFragmentDialog extends  DialogFragment implements View.OnClickListener,
        TextWatcher, LoaderManager.LoaderCallbacks<Cursor> {
    private static final int SUBJECT_LOADER = 300;
    private static final int HOMEWORK_LOADER = 200;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final String TAG = "Dialog_homework";
    private AutoCompleteTextView userInput;
    private EditText hwText;
    private SubjectCursorAdapter subjectCursorAdapter;
    private HomeworkCursorAdapter hwCursorAdapter;
    private Homeworks hw;
    private HomeworkSharedViewModel model;
    private long lsnId=-1;
    private Uri homeworkUri;
    private SimpleDateFormat format;
    Bitmap theImage;
    String photo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(HomeworkSharedViewModel.class);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View addHomeworkView = inflater.inflate(R.layout.activity_add_new_homework, null);
        hwText = addHomeworkView.findViewById(R.id.new_hw_text);

        userInput = addHomeworkView.findViewById(R.id.hw_lesson_name);
        TextView addBtn = addHomeworkView.findViewById(R.id.btn_add_hw);
        TextView titleDlg = addHomeworkView.findViewById(R.id.tv_title_add_hw_dialog);
        format=new SimpleDateFormat("dd.MM.YYYY");

        //встановлюю onClick на кнопки
        addHomeworkView.findViewById(R.id.btn_add_hw).setOnClickListener(this);
        addHomeworkView.findViewById(R.id.btn_cancel_hw).setOnClickListener(this);
        addHomeworkView.findViewById(R.id.hw_photo).setOnClickListener(this);
        //ініціалізую список значень в AutoCompleteTextView
        homeworkUri = model.getHomework_uri();
        initialAutoCompleteTextView();
        Log.i(TAG, "get uri="+homeworkUri);
        titleDlg.setText(R.string.add_hw_title);
        if (homeworkUri==null){
          addBtn.setText(R.string.btn_add);
          titleDlg.setText(R.string.add_hw_title);
        }else{
          titleDlg.setText(R.string.edit_hw_title);
          addBtn.setText(R.string.btn_okey);
        }
        return addHomeworkView;
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            //дії для кнопки Додати
            case R.id.btn_add_hw:
                insertNewHomeWork();
                break;
            // дії для кнопки Фото
            case R.id.hw_photo:
                addPhoto();
            break;
            // дії для кнопки Відмінити
            case R.id.btn_cancel_hw: dismiss();
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
        if (homeworkUri !=null){
            hwCursorAdapter = new HomeworkCursorAdapter(getContext(),null,false);
            getLoaderManager().initLoader(HOMEWORK_LOADER, null,this);
            hw = new Homeworks();
        }
    }
    private void insertNewHomeWork(){
        ContentValues cv = new ContentValues();
        String crnDate = format.format(model.getCrnDate());
        cv.put(HomeworksEntry.DATE_HW,crnDate );
        if(lsnId==-1){
        //предмета немає в БД. Повідомлення про помилку
            Toast.makeText(getContext(), "Такого предмету немає в БД. Оберіть предмет зі списку"
                    , Toast.LENGTH_LONG).show();
        }else {
            String userHWText = hwText.getText().toString();//ДЗ
            cv.put(LessonsEntry.SUBJECT_ID, lsnId);
            cv.put(HomeworksEntry.HOMEWORK, userHWText);
            cv.put(HomeworksEntry.HW_PHOTO,photo);
            cv.put(HomeworksEntry.HW_IS_READY,0);
            ContentResolver contentResolver = getActivity().getContentResolver();
            if (homeworkUri == null) {
                Uri hwInsertUri = contentResolver.insert(HomeworksEntry.HOMEWORK_URI, cv);
                if (hwInsertUri == null) {
                    Toast.makeText(getContext(), "Помилка збереження даних", Toast.LENGTH_LONG).show();
                }
            } else {
               // cv.put(HomeworksEntry.DATE_HW,hw.getDate_hm());
                //cv.put(HomeworksEntry.HW_PHOTO,hw.getPhoto_hw());
                cv.put(HomeworksEntry.HW_IS_READY,hw.isHwIsReady());
                int hwUpdateCount = contentResolver.update(homeworkUri, cv, null, null);
                if (hwUpdateCount == 0) {
                    Toast.makeText(getContext(), "Помилка! Не можу оновити дані :(", Toast.LENGTH_LONG).show();
                }
            }
        }
        Log.i(TAG,"working with day="+ crnDate);
        dismiss();
    }
    private void addPhoto(){
        Log.i(TAG,"Start camera");
        if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        }
        else
        {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            theImage = (Bitmap) data.getExtras().get("data");
            photo=getEncodedString(theImage);
           // setDataToDataBase();
        }
    }
    private String getEncodedString(Bitmap bitmap){

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,100, os);

       /* or use below if you want 32 bit images

        bitmap.compress(Bitmap.CompressFormat.PNG, (0–100 compression), os);*/
        byte[] imageArr = os.toByteArray();

        return Base64.encodeToString(imageArr, Base64.URL_SAFE);

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
        case HOMEWORK_LOADER:
            CursorLoader cursorLoaderHomework = new CursorLoader(getContext(),
                    homeworkUri,null,null,null,null);
        return cursorLoaderHomework;
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
        case HOMEWORK_LOADER:
            hwCursorAdapter.swapCursor(cursor);
            if (cursor.moveToFirst()){
                hw = new Homeworks();
                do {
                    hw.setHm_id(cursor.getInt(cursor.getColumnIndexOrThrow(HomeworksEntry.HM_ID)));
                    hw.setSubject_id(cursor.getInt(cursor.getColumnIndexOrThrow(LessonsEntry.SUBJECT_ID)));
                    hw.setSubject_name(cursor.getString(cursor.getColumnIndexOrThrow(SubjectEntry.KEY_NAME)));
                    hw.setHomework(cursor.getString(cursor.getColumnIndexOrThrow(HomeworksEntry.HOMEWORK)));
                    hw.setDate_hm(cursor.getString(cursor.getColumnIndexOrThrow(HomeworksEntry.DATE_HW)));
                    hw.setPhoto_hw(cursor.getString(cursor.getColumnIndexOrThrow(HomeworksEntry.HW_PHOTO)));
                    hw.setHwIsReady(cursor.getInt(cursor.getColumnIndexOrThrow(HomeworksEntry.HW_IS_READY)));
                }while (cursor.moveToNext());
            }
            userInput.setText(hw.getSubject_name());
            hwText.setText(hw.getHomework());
            lsnId = hw.getSubject_id();
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
                case HOMEWORK_LOADER:
                    hwCursorAdapter.swapCursor(null);
                    break;
            default:break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

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
}
