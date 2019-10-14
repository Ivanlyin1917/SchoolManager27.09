package com.example.myapplication.Model;

public class Lessons {
    private int rec_id;
    private int day_id;
    private int position_id;
    private int subject_id;
    public Lessons(int rec_id,int day_id,int position_id, int subject_id){
        this.day_id=day_id;
        this.position_id=position_id;
        this.rec_id=rec_id;
        this.subject_id=subject_id;
    }
    public Lessons(){

    }
    public Lessons(int day_id,int position_id, int subject_id){
        this.position_id=position_id;
        this.rec_id=rec_id;
        this.subject_id=subject_id;
    }

    public int getRec_id() {
        return rec_id;
    }

    public void setRec_id(int rec_id) {
        this.rec_id = rec_id;
    }

    public int getDay_id() {
        return day_id;
    }

    public void setDay_id(int day_id) {
        this.day_id = day_id;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }
}
