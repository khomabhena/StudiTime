package com.aperturestudios.studytime;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.List;

public class InsertNotes extends AsyncTask<SetterNotes, Void, SetterNotes> {

    private SQLiteDatabase db;
    DBOperations dbOperations;
    private long lastId = 0;
    XMethods xMethods;

    public InsertNotes(Context context) {
        dbOperations = new DBOperations(context);
        db = dbOperations.getWritableDatabase();
        xMethods = new XMethods(context);
    }

    @Override
    protected SetterNotes doInBackground(SetterNotes... params) {
        SetterNotes setter = params[0];

        ContentValues values = new ContentValues();
        values.put(DBContract.Notes.KEY, setter.getKey());
        values.put(DBContract.Notes.PHONE_NUMBER, setter.getPhoneNumber());
        values.put(DBContract.Notes.STUDENT_NAME, setter.getStudentName());
        values.put(DBContract.Notes.LOCAL_URL, setter.getLocalUrl());
        values.put(DBContract.Notes.ONLINE_URL, setter.getOnlineUrl());
        values.put(DBContract.Notes.SUBJECT, setter.getSubject());
        values.put(DBContract.Notes.CHAPTER, setter.getChapter());
        values.put(DBContract.Notes.TOPIC, setter.getTopic());
        values.put(DBContract.Notes.TIMESTAMP, String.valueOf(setter.getTimestamp()));

        lastId = db.insert(DBContract.Notes.TABLE_NAME, null, values);

        return setter;
    }

    @Override
    protected void onPostExecute(SetterNotes setterNotes) {
        xMethods.uploadImageToFirebase(lastId, setterNotes);
    }
}
