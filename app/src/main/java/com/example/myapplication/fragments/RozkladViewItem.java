package com.example.myapplication.fragments;

public class RozkladViewItem {
    private String itemNumber;
    private String lessonTime;
    private String lessonName;
    private String lessonPlace;

    public RozkladViewItem(String itemNumber, String lessonTime, String lessonName, String lessonPlace) {
        this.itemNumber = itemNumber;
        this.lessonTime = lessonTime;
        this.lessonName = lessonName;
        this.lessonPlace = lessonPlace;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public String getLessonTime() {
        return lessonTime;
    }

    public String getLessonName() {
        return lessonName;
    }

    public String getLessonPlace() {
        return lessonPlace;
    }
}
