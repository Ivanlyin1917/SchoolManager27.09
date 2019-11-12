package com.example.myapplication.fragments.homework;

import android.arch.lifecycle.ViewModel;

import java.util.Date;

public class HomeworkSharedViewModel extends ViewModel {
    private Date titleDate;

    public Date getTitleDate() {
        return titleDate;
    }

    public void setTitleDate(Date titleDate) {
        this.titleDate = titleDate;
    }
}
