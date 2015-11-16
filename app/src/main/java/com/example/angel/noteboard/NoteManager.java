package com.example.angel.noteboard;


import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

public class NoteManager {
    private ArrayList<Note> mNotes;

    private static NoteManager sNoteManager;
    private Context mContext;

    private NoteManager(Context appContext){
        mContext = appContext;
        mNotes = new ArrayList<Note>();
        for (int i = 0; i < 10; i++){
            if (i % 2 == 0){
                TextNote tn = new TextNote();
                tn.setContent("Text note " + i);
                mNotes.add(tn);
            }
            else {
                PictureNote pn = new PictureNote();
                mNotes.add(pn);
            }


        }
    }

    public static NoteManager get(Context c){
        if (sNoteManager == null){
            sNoteManager = new NoteManager(c.getApplicationContext());
        }
        return sNoteManager;
    }

    public ArrayList<Note> getNotes(){
        return mNotes;
    }

    public Note getNote(Date date){
        for (Note n : mNotes) {
            if (n.getDate().equals(date))
                return n;
        }
            return null;
    }

}
