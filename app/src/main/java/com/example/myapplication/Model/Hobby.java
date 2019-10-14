package com.example.myapplication.Model;

public class Hobby {
    private int hobby_id;
    private String hobby_name;
    private String hobby_time;
    private  int  day_id;
    private String hobby_place;

    public Hobby (int hobby_id, String hobby_name, int day_id, String hobby_time, String hobby_place) {
        this.hobby_id = hobby_id;
        this.hobby_name = hobby_name;
        this.hobby_time = hobby_time;
        this.day_id = day_id;
        this.hobby_place = hobby_place;
    }
    public Hobby() {
    }
    public Hobby ( String hobby_name, int day_id, String hobby_time, String hobby_place) {
        this.hobby_name = hobby_name;
        this.hobby_time = hobby_time;
        this.day_id = day_id;
        this.hobby_place = hobby_place;
    }



    public int getHobby_id() {
        return hobby_id;
    }
    public  void setHobby_id(int hobby_id){
        this.hobby_id=hobby_id;
    }

    public String getHobby_name(){
        return hobby_name;
    }
    public  void setHobby_name(String hobby_name) {
        this.hobby_name = hobby_name;
    }
     public String getHobby_time(){
        return  hobby_time;
     }
     public void setHobby_time(String hobby_time){
        this.hobby_time=hobby_time;
     }
     public int getDay_id(){
        return day_id;
     }

    public void setDay_id(int day_id) {
        this.day_id = day_id;
    }
    public  String getHobby_place(){
        return  hobby_place;
    }

    public void setHobby_place(String hobby_place) {
        this.hobby_place = hobby_place;
    }
}
