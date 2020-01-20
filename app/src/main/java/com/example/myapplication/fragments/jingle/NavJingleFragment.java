package com.example.myapplication.fragments.jingle;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.Model.Jingle;
import com.example.myapplication.R;
import com.example.myapplication.data.SchoolManagerContract.*;

import java.util.Calendar;

public class NavJingleFragment extends Fragment /*implements JingleTypeFragment.OnArticleSelectedListener */{

    private static final String TAG = "NavJingleFragment";
    private FloatingActionButton fab;
    private int myHour;
    private int myMinute;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View navJingleFragment = inflater.inflate(R.layout.nav_jingle_fragment,container,false);
        fab = navJingleFragment.findViewById(R.id.fab_jingle);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                addNewJingleRec();
            }
        });
        return navJingleFragment;
    }

    private void addNewJingleRec() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity().getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.add_new_jingle_type_rec, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
        alertDialogBuilderUserInput.setView(view);
        final EditText jn = view.findViewById(R.id.new_jingle_name);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Продовжити" , new DialogInterface.OnClickListener() {
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

                if (TextUtils.isEmpty(jn.getText().toString())) {
                    Toast.makeText(getContext(), "Вкажіть назву для розкладу!", Toast.LENGTH_SHORT).show();
                    return;
                } else{
                    alertDialog.dismiss();
                    ContentValues cv = new ContentValues();
                    ContentResolver contentResolver = getActivity().getContentResolver();
                    cv.put(JingleTypeEntry.TYPE_NAME,jn.getText().toString());
                    Uri jingleTypeUri = contentResolver.insert(JingleTypeEntry.JINGLE_TYPE_URI, cv);
                    if (jingleTypeUri == null) {
                        Toast.makeText(getContext(), "Помилка! Не можу зберегти запис", Toast.LENGTH_LONG).show();

                    }else{
                        long jingleTypeId = ContentUris.parseId(jingleTypeUri);
                        showJingleDialog(jingleTypeId, 1);
                    }

                }

            }
        });
    }

    private void showJingleDialog(long jingleTypeId, int count_lesson) {

        int cl = count_lesson+1;
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity().getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.jingle_edit_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
        alertDialogBuilderUserInput.setView(view);

        String title_text = "Час " + count_lesson + " уроку";
        TextView dialogTitle = view.findViewById(R.id.jingle_dlg_title);
        dialogTitle.setText(title_text);

        final EditText inputPos = view.findViewById(R.id.jingle_dlg_position);
        inputPos.setVisibility(View.GONE);
        final TextView jingle_bgn = view.findViewById(R.id.jingle_dlg_bgn);

        jingle_bgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new TimePickerDialog(getContext(), myCallBack, myHour, myMinute, true).show();
            }

            TimePickerDialog.OnTimeSetListener myCallBack = new TimePickerDialog.OnTimeSetListener() {

                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    myHour = hourOfDay;
                    myMinute = minute;
                    String result = String.format("%02d:%02d", myHour, myMinute);
                    jingle_bgn.setText(result);

                }
            };

        });
        final TextView jingle_end = view.findViewById(R.id.jingle_dlg_end);
        jingle_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new TimePickerDialog(getContext(), myCallBack, myHour, myMinute, true).show();
            }

            TimePickerDialog.OnTimeSetListener myCallBack = new TimePickerDialog.OnTimeSetListener() {

                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    myHour = hourOfDay;
                    myMinute = minute;
                    String result = String.format("%02d:%02d", myHour, myMinute);
                    jingle_end.setText(result);

                }
            };
        });
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Наступний урок", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("Закінчити",
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
                if (TextUtils.isEmpty(jingle_bgn.getText().toString()) ||
                        TextUtils.isEmpty(jingle_end.getText().toString())) {
                    Toast.makeText(getContext(), "Не вказано час уроку!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }
                boolean flag = insertJingle(count_lesson, jingle_bgn.getText().toString(),
                        jingle_end.getText().toString(), jingleTypeId);

                if (flag)  showJingleDialog(jingleTypeId,cl);
                else alertDialog.dismiss();
            }
        });
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(jingle_bgn.getText().toString()) ||
                        TextUtils.isEmpty(jingle_end.getText().toString())) {
                    Toast.makeText(getContext(), "Не вказано час уроку!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }
               insertJingle(count_lesson, jingle_bgn.getText().toString(),
                        jingle_end.getText().toString(), jingleTypeId);

            }
        });
    }



    private boolean insertJingle(int count_lesson, String timeBgn, String timeEnd, long jingleTypeId) {
        ContentValues cv = new ContentValues();
        ContentResolver contentResolver = getActivity().getContentResolver();
        cv.put(JingleEntry.POSITION_ID,count_lesson);
        cv.put(JingleEntry.TIME_BEGIN,timeBgn);
        cv.put(JingleEntry.TIME_END,timeEnd);
        cv.put(JingleEntry.JINGLE_TYPE_ID,jingleTypeId);
        Uri jingleInsertUri = contentResolver.insert(JingleEntry.JINGLE_URI, cv);
        if (jingleInsertUri == null) {
            Toast.makeText(getContext(), "Помилка! Не можу зберегти запис", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


//    private void addNewJingleRec() {
//        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity().getApplicationContext());
//        View view = layoutInflaterAndroid.inflate(R.layout.add_new_jingle_type_rec, null);
//
//        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
//        alertDialogBuilderUserInput.setView(view);
////        Calendar mCalendar = Calendar.getInstance();
////        final int  myHour = mCalendar.get(Calendar.HOUR_OF_DAY);
////        final int myMinute = mCalendar.get(Calendar.MINUTE);
//
//
//        final EditText jn = view.findViewById(R.id.new_jingle_name);
//        final EditText jc = view.findViewById(R.id.new_jingle_count);
//        final EditText jl = view.findViewById(R.id.new_jingle_lenght);
//        final EditText jtf = view.findViewById(R.id.new_jingle_time_first);
//        final EditText ftf = view.findViewById(R.id.new_free_first);
//        final EditText fts = view.findViewById(R.id.new_free_second);
//        final EditText ftt = view.findViewById(R.id.new_free_three);
//        final EditText ftfr = view.findViewById(R.id.new_free_four);
//        final EditText ftfv = view.findViewById(R.id.new_free_five);
//        int tfv=Integer.valueOf(ftfv.getText().toString());
//        final EditText ftsx = view.findViewById(R.id.new_free_six);
//        int tsx=Integer.valueOf(ftsx.getText().toString());
//        
//        jtf.setOnClickListener(new View.OnClickListener() {
//
//            Calendar mCalendar = Calendar.getInstance();
//            int  myHour = mCalendar.get(Calendar.HOUR_OF_DAY);
//            int myMinute = mCalendar.get(Calendar.MINUTE);
//            @Override
//            public void onClick(View v) {
//               new TimePickerDialog(getContext(), myCallBack, myHour, myMinute, true).show();
//            }
//            TimePickerDialog.OnTimeSetListener myCallBack = new TimePickerDialog.OnTimeSetListener() {
//
//                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                    myHour = hourOfDay;
//                    myMinute = minute;
//                    String result = String.format("%02d:%02d", myHour,myMinute);
//                    jtf.setText(result);
//
//                }
//            };
//
//        });
//        alertDialogBuilderUserInput
//                .setCancelable(false)
//                .setPositiveButton("Створити" , new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialogBox, int id) {
//
//                    }
//                })
//                .setNegativeButton("Відмінити",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialogBox, int id) {
//                                dialogBox.cancel();
//                            }
//                        });
//
//        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
//        alertDialog.show();
//
//        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
//            int count_free=0;
//            int tf; int ts;
//            int tt; int tfr;
//            @Override
//            public void onClick(View v) {
//                // Show toast message when no text is entered
//                if (TextUtils.isEmpty(jn.getText().toString())) {
//                    Toast.makeText(getContext(), "Вкажіть назву для розкладу дзвінків!", Toast.LENGTH_SHORT).show();
//                    return;
//                } else if (TextUtils.isEmpty(jl.getText().toString())) {
//                        Toast.makeText(getContext(), "Вкажіть тривалість уроку!", Toast.LENGTH_SHORT).show();
//                        return;
//                }else  if (TextUtils.isEmpty(jtf.getText().toString())) {
//                            Toast.makeText(getContext(), "Вкажіть час початку першого уроку!", Toast.LENGTH_SHORT).show();
//                            return;
//                } else if (TextUtils.isEmpty(jc.getText().toString())) {
//                            Toast.makeText(getContext(), "Вкажіть кількість уроків!", Toast.LENGTH_SHORT).show();
//                            return;
//                }else {
//                    if (!TextUtils.isEmpty(ftf.getText().toString())){
//                        tf=Integer.valueOf(ftf.getText().toString());
//                        count_free ++;
//                    }
//                    if (!TextUtils.isEmpty(fts.getText().toString())){
//                        ts=Integer.valueOf(fts.getText().toString());
//                        count_free ++;
//                    }
//                    alertDialog.dismiss();
//                }
//
//
//
//                        // check if user updating note
//                        if ( jingle != null) {
//                            // update note by it's id
//                            updateJingle(inputPos.getText().toString(), jingle_bgn.getText().toString(),
//                                    jingle_end.getText().toString(), jingle_id);
//                        }
//                    }
//
//        });
//    }


}
