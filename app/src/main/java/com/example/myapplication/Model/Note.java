package com.example.myapplication.Model;

public class Note {
    private int noteId;
    private String noteText;
    private String timestamp;

    public Note() {
    }

    public Note(int noteId, String noteText, String timestamp) {
        this.noteId = noteId;
        this.noteText = noteText;
        this.timestamp = timestamp;
    }

    public Note(String noteText, String timestamp) {
        this.noteText = noteText;
        this.timestamp = timestamp;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
