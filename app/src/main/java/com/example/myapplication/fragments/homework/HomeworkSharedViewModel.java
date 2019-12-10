package com.example.myapplication.fragments.homework;

import android.arch.lifecycle.ViewModel;

import java.util.Date;

public class HomeworkSharedViewModel extends ViewModel {
    private String titleDate;
    private int page;

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
