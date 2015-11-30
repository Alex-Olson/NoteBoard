package com.example.angel.noteboard;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Picture;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;

public class DatabaseManager {
    private Context context;
    private SQLHelper helper;
    private SQLiteDatabase db;
    protected static final String DB_NAME = "notes";
    protected static final int DB_VERSION = 1;
    protected static final String DB_TABLE_TEXT = "text_notes";
    protected static final String DB_TABLE_PICTURE = "picture_notes";
    protected static final String dateCol = "date";
    protected static final String contentCol = "content";
    protected static final String uriCol = "uri";
    protected static final String tagsCol = "tags";
    private static final String DBTAG = "DatabaseManager" ;
    private static final String SQLTAG = "SQLHelper" ;
    public DatabaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper(Context c) {
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTextNoteTable = "CREATE TABLE " + DB_TABLE_TEXT + " (" + dateCol + " TEXT, " + contentCol + " TEXT);";
            db.execSQL(createTextNoteTable);

            String createPictureNoteTable = "CREATE TABLE " + DB_TABLE_PICTURE + " (" + dateCol + " TEXT, " + uriCol + " TEXT, " + tagsCol + " TEXT);";
            db.execSQL(createPictureNoteTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_TEXT + ", " + DB_TABLE_PICTURE);
            onCreate(db);
            Log.w(SQLTAG, "Upgrade table - drop and recreate it");
        }
    }

    public boolean addTextRow(String date, String content){
        ContentValues newTextNote = new ContentValues();
        newTextNote.put(dateCol, date);
        newTextNote.put(contentCol, content);
        try{
            db.insertOrThrow(DB_TABLE_TEXT, null, newTextNote);
            return true;
        } catch (Exception e){
            Log.e(DBTAG, "Error inserting new data into table", e);
            return false;
        }
    }

    public boolean addPictureRow(String date, String uri, String tags){
        ContentValues newPictureNote = new ContentValues();
        newPictureNote.put(dateCol, date);
        newPictureNote.put(uriCol, uri);
        newPictureNote.put(tagsCol, tags);
        try{
            db.insertOrThrow(DB_TABLE_PICTURE, null, newPictureNote);
            return true;
        } catch (Exception e){
            Log.e(DBTAG, "Error inserting new data into table", e);
            return false;
        }
    }

    public ArrayList<TextNote> fetchTextNotes(){
        String cols[] = {dateCol, contentCol};
        Cursor cursor = db.query(DB_TABLE_TEXT, cols, null, null, null, null, dateCol);
        cursor.moveToFirst();
        ArrayList<TextNote> tableRows = new ArrayList<>();
        while (!cursor.isAfterLast()){
            TextNote tn = new TextNote();
            tn.setDate(cursor.getString(0));
            tn.setContent(cursor.getString(1));
            tableRows.add(tn);

            cursor.moveToNext();
        }
        if (!cursor.isClosed()){
            cursor.close();
        }
        return tableRows;
    }

    public ArrayList<PictureNote> fetchPictureNotes(){
        String cols[] = {dateCol, uriCol, tagsCol};
        Cursor cursor = db.query(DB_TABLE_PICTURE, cols, null, null, null, null, dateCol);
        cursor.moveToFirst();
        ArrayList<PictureNote> tableRows = new ArrayList<>();
        while (!cursor.isAfterLast()){
            PictureNote pn = new PictureNote();
            pn.setDate(cursor.getString(0));
            pn.setUriString(cursor.getString(1));
            pn.setTags(cursor.getString(2));
            tableRows.add(pn);

            cursor.moveToNext();
        }
        if (!cursor.isClosed()){
            cursor.close();
        }
        return tableRows;
    }
}
