package com.example.myapplication.Model;

public class Teachers {
    private int teacher_id;
    private String surname;
    private String name;
    private String lastName;

    public Teachers(int teacher_id,String surname,String name,String lastName){
        this.teacher_id=teacher_id;
        this.lastName=lastName;
        this.name=name;
        this.surname=surname;
    }
    public Teachers(){

    }
    public Teachers(String surname,String name,String lastName){
        this.lastName=lastName;
        this.name=name;
        this.surname=surname;
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
