package com.aperturestudios.studytime;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.List;

public class InsertTeachers extends AsyncTask<SetterTeachers, Void, Void> {

    private SQLiteDatabase db;
    DBOperations dbOperations;
    List listKeys;

    public InsertTeachers(Context context, List listKeys) {
        dbOperations = new DBOperations(context);
        db = dbOperations.getWritableDatabase();
        this.listKeys = listKeys;
    }

    @Override
    protected Void doInBackground(SetterTeachers... params) {
        SetterTeachers setter = params[0];


        ContentValues values = new ContentValues();
        values.put(DBContract.Teacher.NAME, setter.getName());

        if (!listKeys.contains(setter.getName()))
            db.insert(DBContract.Teacher.TABLE_NAME, null, values);

        listKeys = dbOperations.getTeacherKeys(db);

        return null;
    }

}
