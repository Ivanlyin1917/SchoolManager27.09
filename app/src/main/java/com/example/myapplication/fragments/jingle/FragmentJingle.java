package com.example.myapplication.fragments.jingle;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.JingleCursorAdapter;
import com.example.myapplication.data.SchoolManagerContract;

public class FragmentJingle extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "Jingle";
    private static final int JINGLE_LOADER = 800;
    private int jingleTypeId;
    private JingleCursorAdapter jingleCursorAdapter;

    public FragmentJingle() {
    }

    public static FragmentJingle newInstance(int jingleTypeId){
        FragmentJingle fragment = new FragmentJingle();
        Bundle argm = new Bundle();
        argm.putInt("jingleTypeId", jingleTypeId);
        fragment.setArguments(argm);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jingleTypeId = getArguments().getInt("jingleTypeId");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(JINGLE_LOADER,null,this);
        jingleCursorAdapter = new JingleCursorAdapter(getContext(),null, false);
        View result=inflater.inflate(R.layout.fragment_jingle, container, false);//створюэмо фрагмент
        ListView jingleList = result.findViewById(R.id.jingle_list);
        jingleList.setAdapter(jingleCursorAdapter);
        return result;
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
}
