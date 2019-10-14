package com.example.myapplication.Model;

public class Days {
    private int day_id;
    private String day_name;
    private int is_school_day;
    private int jingle_type_id;

    public Days(int day_id, String day_name, int is_school_day, int jingle_type_id) {
        this.day_id=day_id;
        this.day_name=day_name;
        this.is_school_day=is_school_day;
        this.jingle_type_id= jingle_type_id;

    }
    public Days(){

    }
    public Days( String day_name, int is_school_day, int jingle_type_id) {
        this.day_name=day_name;
        this.is_school_day=is_school_day;
        this.jingle_type_id= jingle_type_id;
}

    public int getDay_id() {
        return day_id;
    }

    public void setDay_id(int day_id) {
        this.day_id = day_id;
    }

    public String getDay_name() {
        return day_name;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }

    public int getIs_school_day() {
        return is_school_day;
    }

    public void setIs_school_day(int is_school_day) {
        this.is_school_day = is_school_day;
    }

    public int getJingle_type_id() {
        return jingle_type_id;
    }

    public void setJingle_type_id(int jingle_type_id) {
        this.jingle_type_id = jingle_type_id;
    }
}

