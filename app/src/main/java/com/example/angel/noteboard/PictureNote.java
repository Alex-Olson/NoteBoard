package com.example.angel.noteboard;


import java.util.Date;

public class PictureNote extends Note{
//    private Date mDate;
    private String mTags;
    private String mUriString;

    public PictureNote() {
        super();
    }

    public String getUriString() {
        return mUriString;
    }

    public void setUriString(String mUriString) {
        this.mUriString = mUriString;
    }

//        public Date getDate() {
//            return mDate;
//        }

        public String getTags() {
            return mTags;
        }

        public void setTags(String tags) {
            this.mTags = tags;
        }

        @Override
        public String toString(){
            return mTags;
        }
}
