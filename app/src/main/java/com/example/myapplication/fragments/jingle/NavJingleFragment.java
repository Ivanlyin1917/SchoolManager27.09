package com.example.myapplication.fragments.jingle;

import android.content.ContentUris;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;

public class NavJingleFragment extends Fragment /*implements JingleTypeFragment.OnArticleSelectedListener */{
    private static final String TAG = "NavJingleFragment";
    private FragmentJingle  fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View navJingleFragment = inflater.inflate(R.layout.nav_jingle_fragment,container,false);
        return navJingleFragment;
    }

    /*@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_jingle_fragment);
    }*/

  /*  @Override
    public void onArticleSelected(Uri articleUri) {
        long typeId = ContentUris.parseId(articleUri);
        fragment = FragmentJingle.newInstance(typeId);
       // fragment = new FragmentJingle();

       getFragmentManager().beginTransaction().replace(R.id.container_jingle,fragment).commit();


    }*/
}
