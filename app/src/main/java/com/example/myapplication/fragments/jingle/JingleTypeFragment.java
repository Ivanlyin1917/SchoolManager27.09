package com.example.myapplication.fragments.jingle;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Model.JingleType;
import com.example.myapplication.R;
import com.example.myapplication.adapter.JingleTypeCursorAdapter;
import com.example.myapplication.data.SchoolManagerContract.*;

public class JingleTypeFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemLongClickListener {
    private static final String TAG = "JingleType";
    private static final int JINGLE_TYPE_LOADER = 700;
    private JingleTypeCursorAdapter jingleTypeCursorAdapter;
    private OnArticleSelectedListener mLisener;
    private ListView list;
    private View v;
    private int position;
    private long id;

    public interface OnArticleSelectedListener{
        public void onArticleSelected(Uri articleUri);
    }

   @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnArticleSelectedListener)
            mLisener = (OnArticleSelectedListener) context;
        else
            throw new ClassCastException(context.toString() + " must implement onArticleSelectedLisener!");

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
       Uri jingleTypeUri = ContentUris.withAppendedId(JingleTypeEntry.JINGLE_TYPE_URI,id);
       mLisener.onArticleSelected(jingleTypeUri);
       setSelection(position);
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(JINGLE_TYPE_LOADER, null,this);
        jingleTypeCursorAdapter = new JingleTypeCursorAdapter(getContext(), null, false);
        View fragmentJingleType = inflater.inflate(R.layout.fragment_jingle_type, container, false);
        setListAdapter(jingleTypeCursorAdapter);

        //list = getListView();
       //

        return fragmentJingleType;
    }



    @Override
    public void onStart() {

        super.onStart();

    }

    @Override
    public void onResume() {
        list = getListView();
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setOnItemLongClickListener(this);
        super.onResume();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) parent.getAdapter().getItem(position);
        JingleType jingleType = new JingleType();
        jingleType.setJingle_type_id(cursor.getLong(cursor.getColumnIndexOrThrow(JingleTypeEntry.JINGLE_TYPE_ID)));
        jingleType.setType_name(cursor.getString(cursor.getColumnIndexOrThrow(JingleTypeEntry.TYPE_NAME)));
        showAlertDialog(id, jingleType);
        return true;
    }

    private void showAlertDialog(long jingleTypeId, JingleType jingleType){
        CharSequence colors[] = new CharSequence[]{"Редагувати", "Вилучити","Обрати день"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Оберіть дію");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:  showJingleTypeDialog(jingleType);
                    break;
                    case 1: deleteJingleType(jingleTypeId);
                    break;
                    case 2: break;
                    default: break;

                }
            }
        });
        builder.show();
    }

    private void deleteJingleType(long typeId){
        Uri jingleTypeUri = ContentUris.withAppendedId(JingleTypeEntry.JINGLE_TYPE_URI,typeId);
        ContentResolver cr = getActivity().getContentResolver();
        Log.i(TAG, "begin delete jingleType with id ="+typeId);
        int countDelJingle = cr.delete(JingleEntry.JINGLE_URI,null,new String[]{String.valueOf(typeId)});
        Log.i(TAG, "delete "+countDelJingle+" records about jingle") ;
        int countDelRec = cr.delete(jingleTypeUri,null,null);
        Log.i(TAG, "delete "+countDelRec+" jingleType with Uri = "+jingleTypeUri) ;
        if(countDelRec==0){
            Toast.makeText(getContext(), "Помилка! Не можу вилучити запис!", Toast.LENGTH_LONG).show();
        }
    }

    private void  showJingleTypeDialog(JingleType jingleType){
        final long jingleTypeId =jingleType.getJingle_type_id();;
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity().getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.add_new_jingle_type_rec, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
        alertDialogBuilderUserInput.setView(view);
        final EditText jn = view.findViewById(R.id.new_jingle_name);
        if (jingleType!=null) jn.setText(jingleType.getType_name());

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Оновити" , new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("Відмінити", new DialogInterface.OnClickListener() {
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
                    updateJingleType(jingleTypeId, jn.getText().toString());

                }

            }
        });
    }
    private void updateJingleType(long typeId, String typeName){
        Uri uri = ContentUris.withAppendedId(JingleTypeEntry.JINGLE_TYPE_URI,typeId);
        ContentValues cv = new ContentValues();
        ContentResolver contentResolver = getActivity().getContentResolver();
        cv.put(JingleTypeEntry.TYPE_NAME,typeName);
        int countRec = contentResolver.update(uri, cv,null,null);
        if (countRec  == 0) {
            Toast.makeText(getContext(), "Помилка! Не можу оновити запис", Toast.LENGTH_LONG).show();

        }
    }


    @NonNull
    @Override
    public Loader onCreateLoader(int i, @Nullable Bundle bundle) {
        CursorLoader cursorLoader = new CursorLoader(getContext(),
                JingleTypeEntry.JINGLE_TYPE_URI,null,null,
                null,null);
        Log.i(TAG, "select all records for jingle_type");
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Cursor cursor) {
        jingleTypeCursorAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        jingleTypeCursorAdapter.swapCursor(null);

    }
}
