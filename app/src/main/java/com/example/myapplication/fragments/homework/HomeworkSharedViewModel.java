package com.example.myapplication.fragments.homework;

import android.arch.lifecycle.ViewModel;
import android.net.Uri;

import com.example.myapplication.adapter.HomeworkCursorAdapter;

import java.util.Date;

public class HomeworkSharedViewModel extends ViewModel {
    private Date crnDate;
    private String titleDate;
    private int page;
    private HomeworkCursorAdapter homeworkCursorAdapter;
    private Uri homework_uri;


    public Date getCrnDate() {
        return crnDate;
    }

    public void setCrnDate(Date crnDate) {
        this.crnDate = crnDate;
    }

    public HomeworkCursorAdapter getHomeworkCursorAdapter() {
        return homeworkCursorAdapter;
    }

    public void setHomeworkCursorAdapter(HomeworkCursorAdapter homeworkCursorAdapter) {
        this.homeworkCursorAdapter = homeworkCursorAdapter;
    }

    public Uri getHomework_uri() {
        return homework_uri;
    }

    public void setHomework_uri(Uri homework_uri) {
        this.homework_uri = homework_uri;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getTitleDate() {
        return titleDate;
    }

    public void setTitleDate(String titleDate) {
        this.titleDate = titleDate;
    }
}
