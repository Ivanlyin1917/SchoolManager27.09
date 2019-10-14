package com.example.myapplication.Model;

public class Jingle {
    private int jingle_id;
    private int position_id;
    private String time_begin;
    private String time_end;
    private int jingle_type_id;

    public Jingle(int jingle_id, int position_id, String time_begin, String time_end, int jingle_type_id) {
        this.jingle_id = jingle_id;
        this.jingle_type_id = jingle_type_id;
        this.time_begin = time_begin;
        this.time_end = time_end;
        this.position_id = position_id;
    }

    public Jingle() {

    }

    public Jingle(int position_id, String time_begin, String time_end, int jingle_type_id) {
        this.jingle_type_id = jingle_type_id;
        this.time_begin = time_begin;
        this.time_end = time_end;
        this.position_id = position_id;
    }

    public int getJingle_type_id() {
        return jingle_type_id;
    }


    public void setJingle_type_id(int jingle_type_id) {
        this.jingle_type_id = jingle_type_id;
    }


    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public String getTime_begin() {
        return time_begin;
    }

    public void setTime_begin(String time_begin) {
        this.time_begin = time_begin;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public int getJingle_id() {
        return jingle_id;
    }

    public void setJingle_id(int jingle_id) {
        this.jingle_id = jingle_id;
    }
}