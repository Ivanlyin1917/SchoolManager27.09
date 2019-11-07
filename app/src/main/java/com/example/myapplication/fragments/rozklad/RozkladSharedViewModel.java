package com.example.myapplication.fragments.rozklad;

import android.arch.lifecycle.ViewModel;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.myapplication.adapter.RozkladCursorAdapter;

public class RozkladSharedViewModel extends ViewModel {
    private int weekDay;
    private Uri lessonUri;
    private RozkladCursorAdapter rozkladCursorAdapter;

    public void setWeekDay(int n){
        this.weekDay = n;
    }
    public int getWeekDay(){
        return this.weekDay;
    }
    public void setLessonUri(@Nullable Uri uri){this.lessonUri=uri;}
    public Uri getLessonUri() {
        return lessonUri;
    }

    public RozkladCursorAdapter getRozkladCursorAdapter() {
        return rozkladCursorAdapter;
    }

    public void setRozkladCursorAdapter(RozkladCursorAdapter rozkladCursorAdapter) {
        this.rozkladCursorAdapter = rozkladCursorAdapter;
    }
}
