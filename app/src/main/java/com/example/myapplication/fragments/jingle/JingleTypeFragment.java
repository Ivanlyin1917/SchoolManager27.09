package com.example.myapplication.fragments.jingle;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import com.example.myapplication.adapter.JingleTypeCursorAdapter;
import com.example.myapplication.data.SchoolManagerContract;

public class JingleTypeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "JingleType";
    private static final int JINGLE_TYPE_LOADER = 700;
    private JingleTypeCursorAdapter jingleTypeCursorAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(JINGLE_TYPE_LOADER, null,this);
        jingleTypeCursorAdapter = new JingleTypeCursorAdapter(getContext(), null, false);
        View fragmentJingleType = inflater.inflate(R.layout.fragment_jingle_type, container, false);
        ListView jingleTypeList = fragmentJingleType.findViewById(R.id.jingle_type_list);
        jingleTypeList.setAdapter(jingleTypeCursorAdapter);
        FloatingActionButton fab = fragmentJingleType.findViewById(R.id.fab_jingle_type);

        return fragmentJingleType;
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int i, @Nullable Bundle bundle) {
        CursorLoader cursorLoader = new CursorLoader(getContext(),
                SchoolManagerContract.JingleTypeEntry.JINGLE_TYPE_URI,null,null,
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
