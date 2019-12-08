package com.example.myapplication.fragments.homework;

import android.arch.lifecycle.ViewModel;

import java.util.Date;

public class HomeworkSharedViewModel extends ViewModel {
    private Date titleDate;
    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Date getTitleDate() {
        return titleDate;
    }

    public void setTitleDate(Date titleDate) {
        this.titleDate = titleDate;
    }
}
