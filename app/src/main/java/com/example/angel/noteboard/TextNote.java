package com.example.angel.noteboard;


import java.util.Date;

public class TextNote extends Note{
//    private Date mDate;
    private String mContent;

    public TextNote() {
        super();
    }

//    public Date getDate() {
//        return mDate;
//    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    @Override
    public String toString(){
        return mContent;
    }






}
