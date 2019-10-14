package com.example.myapplication.Model;

public class TeachersSubject {
    private int ts_id;
    private String teacher_id;
    private String subject_id;

    public TeachersSubject(int ts_id,String teacher_id,String subject_id){
        this.ts_id=ts_id;
        this.teacher_id=teacher_id;
        this.subject_id=subject_id;
    }
    public TeachersSubject(){

    }
    public TeachersSubject(String teacher_id,String subject_id){
        this.teacher_id=teacher_id;
        this.subject_id=subject_id;
    }

    public int getTs_id() {
        return ts_id;
    }

    public void setTs_id(int ts_id) {
        this.ts_id = ts_id;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }
}
