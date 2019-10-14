package com.example.myapplication.Model;

public class Homeworks {
    private int hm_id;
    private String date_hm;
    private int subject_id;
    private String homework;

    public Homeworks(int hm_id, String date_hm, int subject_id, String homework) {
        this.hm_id = hm_id;
        this.date_hm = date_hm;
        this.subject_id = subject_id;
        this.homework = homework;
    }

    public Homeworks() {

    }

    public Homeworks(String date_hm, int subject_id, String homework) {
        this.date_hm = date_hm;
        this.subject_id = subject_id;
        this.homework = homework;

    }

    public int getHm_id() {
        return hm_id;
    }

    public void setHm_id(int hm_id) {
        this.hm_id = hm_id;
    }
    public String getDate_hm(){
        return date_hm;
    }

    public void setDate_hm(String date_hm) {
        this.date_hm = date_hm;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }
}