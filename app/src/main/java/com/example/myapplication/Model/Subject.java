package com.example.myapplication.Model;

public class Subject {
    private int id;
    private String name;

    public Subject(int id, String name, int type) {
        this.id = id;
        this.name = name;
    }

    public Subject() {
    }

    public Subject(String name, int type) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
