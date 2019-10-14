package com.example.myapplication.Model;

public class JingleType {
    private int jingle_type_id;
    private String type_name;

    public JingleType(int jingle_type_id,String type_name){
        this.jingle_type_id=jingle_type_id;
        this.type_name=type_name;
    }
    public JingleType(){

    }
    public JingleType(String type_name){
        this.type_name=type_name;
    }

    public int getJingle_type_id() {
        return jingle_type_id;
    }

    public void setJingle_type_id(int jingle_type_id) {
        this.jingle_type_id = jingle_type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }
}
