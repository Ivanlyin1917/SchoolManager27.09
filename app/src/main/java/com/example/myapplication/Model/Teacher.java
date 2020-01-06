package com.example.myapplication.Model;

public class Teacher {
    private int teacher_id;
    private String surname;
    private String name;
    private String lastName;
    private  String subject;
    private  int subject_id;


    public Teacher(){

    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public Teacher(int teacher_id, String surname, String name, String lastName, String subject, int subject_id) {
        this.teacher_id = teacher_id;
        this.surname = surname;
        this.name = name;
        this.lastName = lastName;
        this.subject = subject;
        this.subject_id = subject_id;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
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
