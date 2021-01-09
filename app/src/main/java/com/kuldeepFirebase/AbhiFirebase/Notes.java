package com.kuldeepFirebase.AbhiFirebase;

import java.io.Serializable;

class Notes implements Serializable {

    String noteTitle = "";
    String noteDesc = "";
    String noteDate = "";
    String noteID = "";

    public Notes() {
    }

    public Notes(String noteTitle, String noteDesc, String noteDate, String noteID) {
        this.noteTitle=noteTitle;
        this.noteDesc=noteDesc;
        this.noteDate=noteDate;
        this.noteID=noteID;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle=noteTitle;
    }

    public String getNoteDesc() {
        return noteDesc;
    }

    public void setNoteDesc(String noteDesc) {
        this.noteDesc=noteDesc;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate=noteDate;
    }

    public String getNoteID() {
        return noteID;
    }

    public void setNoteID(String noteID) {
        this.noteID=noteID;
    }
}
