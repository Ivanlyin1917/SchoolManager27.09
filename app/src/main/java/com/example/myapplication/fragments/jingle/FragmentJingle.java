package com.example.myapplication.fragments.jingle;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.Model.Jingle;
import com.example.myapplication.R;
import com.example.myapplication.adapter.JingleCursorAdapter;
import com.example.myapplication.data.SchoolManagerContract;

import org.w3c.dom.Text;

import java.util.Date;

public class FragmentJingle extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemLongClickListener {

    private static final String TAG = "Jingle";
    private static final int JINGLE_LOADER = 800;
    private long jingleTypeId;
    private JingleCursorAdapter jingleCursorAdapter;
    private int myHour;
    private int myMinute;

    public FragmentJingle() {
    }

    public static FragmentJingle newInstance (long typeId){
        FragmentJingle fragmentJingle = new FragmentJingle();
        Bundle bundle = new Bundle();
        bundle.putLong("jingleTypeId",typeId);
        fragmentJingle.setArguments(bundle);
        return fragmentJingle;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jingleTypeId = getArguments().getLong("jingleTypeId");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(JINGLE_LOADER,null,this);
        jingleCursorAdapter = new JingleCursorAdapter(getContext(),null, false);
        View result=inflater.inflate(R.layout.fragment_jingle, container, false);//створюэмо фрагмент
        ListView jingleList = result.findViewById(R.id.jingle_list);
        jingleList.setAdapter(jingleCursorAdapter);
        jingleList.setOnItemLongClickListener(this);
                ///new AdapterView.OnItemLongClickListener() {

//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                boolean flag = true;
//                Cursor mCursor = (Cursor) parent.getAdapter().getItem(position);
//                Jingle jingle = new Jingle();
//                //jingle.setJingle_id(id);
//                jingle.setJingle_id(mCursor.getInt(mCursor.getColumnIndexOrThrow(SchoolManagerContract.JingleEntry.JINGLE_ID)));
//                jingle.setPosition_id(mCursor.getInt(mCursor.getColumnIndexOrThrow(SchoolManagerContract.JingleEntry.POSITION_ID)));
//                jingle.setJingle_type_id(mCursor.getInt(mCursor.getColumnIndexOrThrow(SchoolManagerContract.JingleEntry.JINGLE_TYPE_ID)));
//                jingle.setTime_begin(mCursor.getString(mCursor.getColumnIndexOrThrow(SchoolManagerContract.JingleEntry.TIME_BEGIN)));
//                jingle.setTime_end(mCursor.getString(mCursor.getColumnIndexOrThrow(SchoolManagerContract.JingleEntry.TIME_END)));
//
//                showActionsDialog(jingle);
//                return flag;
//            }
        //});
        return result;
    }
    private void showActionsDialog(final Jingle jingle) {
        CharSequence colors[] = new CharSequence[]{"Редагувати", "Вилучити"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Оберіть дію");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showJingleDialog(jingle);
                } else {
                    int jingle_id = jingle.getJingle_id();
                    deleteJingle(jingle_id);
                }
            }
        });
        builder.show();
    }

    private void deleteJingle(int jingle_id) {

        Uri jingleUri = ContentUris.withAppendedId(SchoolManagerContract.JingleEntry.JINGLE_URI,jingle_id);
        ContentResolver cr = getActivity().getContentResolver();
        Log.i(TAG, "begin delete jingle with id ="+jingle_id);
        int countDelRec = cr.delete(jingleUri,null,null);
        Log.i(TAG, "delete "+countDelRec+" jingle") ;
        if(countDelRec==0){
            Toast.makeText(getContext(), "Помилка! Не можу вилучити час уроку!", Toast.LENGTH_LONG).show();
        }
    }

    private void showJingleDialog(final Jingle jingle) {
        long  jingle_id = jingle.getJingle_id();
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity().getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.jingle_edit_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
        alertDialogBuilderUserInput.setView(view);

        TextView dialogTitle = view.findViewById(R.id.jingle_dlg_title);
        dialogTitle.setText(getString(R.string.edit_jingle_time_title));

        final EditText inputPos = view.findViewById(R.id.jingle_dlg_position);
        final TextView jingle_bgn = view.findViewById(R.id.jingle_dlg_bgn);
        jingle_bgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time =((TextView) v).getText().toString();
                myHour = Integer.valueOf(time.substring(0,1));
                myMinute = Integer.valueOf(time.substring(3,4));
                //showTimeDialog(v);
                new TimePickerDialog(getContext(), myCallBack, myHour, myMinute, true).show();
            }
            TimePickerDialog.OnTimeSetListener myCallBack = new TimePickerDialog.OnTimeSetListener() {

                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    myHour = hourOfDay;
                    myMinute = minute;
                    String result = String.format("%02d:%02d", myHour,myMinute);
                    jingle_bgn.setText(result);

                }
            };

        });
        final TextView jingle_end = view.findViewById(R.id.jingle_dlg_end);
        jingle_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time =((TextView) v).getText().toString();
                myHour = Integer.valueOf(time.substring(0,1));
                myMinute = Integer.valueOf(time.substring(3,4));
                //showTimeDialog(v);
                new TimePickerDialog(getContext(), myCallBack, myHour, myMinute, true).show();
            }
            TimePickerDialog.OnTimeSetListener myCallBack = new TimePickerDialog.OnTimeSetListener() {

                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    myHour = hourOfDay;
                    myMinute = minute;
                    String result = String.format("%02d:%02d", myHour,myMinute);
                    jingle_end.setText(result);

                }
            };
        });
        if (jingle != null) {
            inputPos.setText(String.valueOf(jingle.getPosition_id()));
            jingle_bgn.setText(jingle.getTime_begin());
            jingle_end.setText(jingle.getTime_end());

        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Оновити" , new DialogInterface.OnClickListener() {
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
                if (TextUtils.isEmpty(inputPos.getText().toString())) {
                    Toast.makeText(getContext(), "Вкажіть номер уроку!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // check if user updating note
                if ( jingle != null) {
                    // update note by it's id
                    updateJingle(inputPos.getText().toString(), jingle_bgn.getText().toString(),
                            jingle_end.getText().toString(), jingle_id);
                }
            }
        });
    }



    private void updateJingle(String jinglePos, String jingleBgn, String jingleEnd, long jingle_id) {
        Uri jingleUri = ContentUris.withAppendedId(SchoolManagerContract.JingleEntry.JINGLE_URI,jingle_id);
        ContentValues cv = new ContentValues();
        ContentResolver contentResolver = getActivity().getContentResolver();
        cv.put(SchoolManagerContract.JingleEntry.POSITION_ID,jinglePos);
        cv.put(SchoolManagerContract.JingleEntry.TIME_BEGIN, jingleBgn);
        cv.put(SchoolManagerContract.JingleEntry.TIME_END,jingleEnd);
        Log.i(TAG, "begin update jingle with id ="+jingle_id);
        int jingleUpdateCount = contentResolver.update(jingleUri, cv, null, null);
        Log.i(TAG, "Update "+jingleUpdateCount+" note") ;
        if (jingleUpdateCount == 0) {
            Toast.makeText(getContext(), "Помилка! Не можу оновити час уроку :(", Toast.LENGTH_LONG).show();
        }
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        CursorLoader cursorLoader = new CursorLoader(getContext(),
                SchoolManagerContract.JingleEntry.JINGLE_URI,null,null,
                new String[]{String.valueOf(jingleTypeId)},null);
        Log.i(TAG, "select all jingle for jingle_type = "+jingleTypeId);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        jingleCursorAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        jingleCursorAdapter.swapCursor(null);

    }

    @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor mCursor = (Cursor) parent.getAdapter().getItem(position);
                Jingle jingle = new Jingle();
                //jingle.setJingle_id(id);
                jingle.setJingle_id(mCursor.getInt(mCursor.getColumnIndexOrThrow(SchoolManagerContract.JingleEntry.JINGLE_ID)));
                jingle.setPosition_id(mCursor.getInt(mCursor.getColumnIndexOrThrow(SchoolManagerContract.JingleEntry.POSITION_ID)));
                jingle.setJingle_type_id(mCursor.getInt(mCursor.getColumnIndexOrThrow(SchoolManagerContract.JingleEntry.JINGLE_TYPE_ID)));
                jingle.setTime_begin(mCursor.getString(mCursor.getColumnIndexOrThrow(SchoolManagerContract.JingleEntry.TIME_BEGIN)));
                jingle.setTime_end(mCursor.getString(mCursor.getColumnIndexOrThrow(SchoolManagerContract.JingleEntry.TIME_END)));

                showActionsDialog(jingle);
                return true;
            }
}
