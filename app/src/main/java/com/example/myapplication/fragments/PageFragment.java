package com.example.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

public class PageFragment extends Fragment {
    private int pageNumber;

    public static PageFragment newInstance(int page) {
        PageFragment fragment = new PageFragment();
        Bundle args=new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    public static String getTitle(Context context, int position){
       String title="";
       switch (position+1){
           case 1: title = "Понеділок";
                   break;
           case 2: title = "Вівторок";
               break;
           case 3: title = "Середа";
               break;
           case 4: title = "Четвер";
               break;
           case 5: title = "П\'ятниця";
               break;
           case 6: title = "Субота";
               break;
           case 7: title = "Неділя";
               break;
       }
       return  title;
    }

    public PageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.page_fragment, container, false);//створюэмо фрагмент
        TextView pageHeader=(TextView)result.findViewById(R.id.displayText); //отримуэмо TextView з розмітки фрагмента
        String header = String.format("Фрагмент %d", pageNumber+1);
        pageHeader.setText(header);
        return result;
    }
}

