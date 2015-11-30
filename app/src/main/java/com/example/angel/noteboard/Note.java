package com.example.angel.noteboard;


import java.util.Date;

public class Note {

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    private String mDate;

    public Note(){
        mDate = new Date().toString();
    }

    public String getDate() {
        return mDate;
    }


}
