package com.example.myapplication.fragments.rozklad;

public class RozkladViewItem {

    private long id;
    private long subjectId;
   // private String lessonTime;
    private String lessonName;
    private String lessonPlace;

    public RozkladViewItem() {
    }

    public RozkladViewItem(long id, long subjectId, String lessonName, String lessonPlace) {
        this.id = id;
        this.subjectId = subjectId;
        this.lessonName = lessonName;
        this.lessonPlace = lessonPlace;
    }

    public RozkladViewItem(long subjectId, /*String lessonTime, */String lessonName, String lessonPlace) {
        this.subjectId = subjectId;
        //this.lessonTime = lessonTime;
        this.lessonName = lessonName;
        this.lessonPlace = lessonPlace;
    }

    public long getId() {
        return id;
    }

    public long getSubjectId() {
        return subjectId;
    }

   /* public String getLessonTime() {
        return lessonTime;
    }*/

    public String getLessonName() {
        return lessonName;
    }

    public String getLessonPlace() {
        return lessonPlace;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public void setLessonPlace(String lessonPlace) {
        this.lessonPlace = lessonPlace;
    }
}
