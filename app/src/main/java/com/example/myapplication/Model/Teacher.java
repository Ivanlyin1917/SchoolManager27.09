package com.example.myapplication.Model;

public class Teacher {
    private long teacher_id;
    private String surname;
    private String name;
    private String lastName;
    private  String subject;
    private  long subject_id;
    private long ts_id;


    public Teacher(){

    }

    public Teacher(long teacher_id, String surname, String name, String lastName, String subject, long subject_id, long ts_id) {
        this.teacher_id = teacher_id;
        this.surname = surname;
        this.name = name;
        this.lastName = lastName;
        this.subject = subject;
        this.subject_id = subject_id;
        this.ts_id = ts_id;
    }

    public long getTs_id() {
        return ts_id;
    }

    public void setTs_id(long ts_id) {
        this.ts_id = ts_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public long getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(long subject_id) {
        this.subject_id = subject_id;
    }

    public long getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(long teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
