package com.example.angel.noteboard;


import android.content.Context;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

public class NoteManager {
    private ArrayList<Note> mNotes;
    private DatabaseManager DBManager;



    private static NoteManager sNoteManager;
    private Context mContext;

    private NoteManager(Context appContext){
        mContext = appContext;
        mNotes = new ArrayList<Note>();
        DBManager = new DatabaseManager(mContext);
        mNotes.addAll(DBManager.fetchTextNotes());
        mNotes.addAll(DBManager.fetchPictureNotes());



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

    public Note getNote(String date){
        for (Note n : mNotes) {
            if (n.getDate().equals(date))
                return n;
        }
            return null;
    }

    public void addNote(Note n){
        if (n instanceof TextNote)
        {
            DBManager.addTextRow(n.getDate().toString(), ((TextNote) n).getContent());
        }
        else if (n instanceof PictureNote)
        {
            DBManager.addPictureRow(n.getDate().toString(), ((PictureNote) n).getUriString(), ((PictureNote) n).getTags());
        }

        mNotes.add(n);
    }

}
