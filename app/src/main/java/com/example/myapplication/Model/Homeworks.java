package com.example.myapplication.Model;

public class Homeworks {
    private long hm_id;
    private long date_hm;
    private String subject_name;
    private int subject_id;

    private String homework;

    public Homeworks(int hm_id, long date_hm, String subject_name, String homework) {
        this.hm_id = hm_id;
        this.date_hm = date_hm;
        this.subject_name = subject_name;
        this.homework = homework;
    }

    public Homeworks() {

    }

    public Homeworks(long date_hm, String subject_name, String homework) {
        this.date_hm = date_hm;
        this.subject_name = subject_name;
        this.homework = homework;

    }

    public long getHm_id() {
        return hm_id;
    }

    public void setHm_id(long hm_id) {
        this.hm_id = hm_id;
    }

    public long getDate_hm(){
        return date_hm;
    }

    public void setDate_hm(long date_hm) {
        this.date_hm = date_hm;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }
}