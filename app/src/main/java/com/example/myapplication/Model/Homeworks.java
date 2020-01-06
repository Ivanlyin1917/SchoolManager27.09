package com.example.myapplication.Model;

public class Homeworks {
    private long hm_id;
    private String date_hm;
    //private long date_hm;
    private String subject_name;
    private int subject_id;
    private String homework;
    private String photo_hw;
    private int hwIsReady;

//    public Homeworks(int hm_id, long date_hm, String subject_name, String homework) {
//        this.hm_id = hm_id;
//        this.date_hm = date_hm;
//        this.subject_name = subject_name;
//        this.homework = homework;
//    }

    public Homeworks() {

    }

    public Homeworks(long hm_id, String date_hm, String subject_name, int subject_id,
                     String homework, String photo, int isReady) {
        this.hm_id = hm_id;
        this.date_hm = date_hm;
        this.subject_name = subject_name;
        this.subject_id = subject_id;
        this.homework = homework;
        this.photo_hw = photo;
        this.hwIsReady = isReady;
    }

    public Homeworks(String date_hm, String subject_name, int subject_id,
                     String homework, String photo, int isReady) {
        this.date_hm = date_hm;
        this.subject_name = subject_name;
        this.subject_id = subject_id;
        this.homework = homework;
        this.photo_hw = photo;
        this.hwIsReady = isReady;
    }

    //    public Homeworks(long date_hm, String subject_name, String homework) {
//        this.date_hm = date_hm;
//        this.subject_name = subject_name;
//        this.homework = homework;
//
//    }


    public String getPhoto_hw() {
        return photo_hw;
    }

    public void setPhoto_hw(String photo_hw) {
        this.photo_hw = photo_hw;
    }

    public int isHwIsReady() {
        return hwIsReady;
    }

    public void setHwIsReady(int hwIsReady) {
        this.hwIsReady = hwIsReady;
    }

    public long getHm_id() {
        return hm_id;
    }

    public void setHm_id(long hm_id) {
        this.hm_id = hm_id;
    }

    public String getDate_hm() {
        return date_hm;
    }

    public void setDate_hm(String date_hm) {
        this.date_hm = date_hm;
    }
    //    public long getDate_hm(){ return date_hm; }
//
//    public void setDate_hm(long date_hm) {
//        this.date_hm = date_hm;
//    }

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