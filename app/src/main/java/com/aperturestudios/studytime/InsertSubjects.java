package com.aperturestudios.studytime;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.List;

public class InsertSubjects extends AsyncTask<SetterSubjects, Void, Void> {

    private SQLiteDatabase db;
    DBOperations dbOperations;
    List listKeys;

    public InsertSubjects(Context context, List listKeys) {
        dbOperations = new DBOperations(context);
        db = dbOperations.getWritableDatabase();
        this.listKeys = listKeys;
    }

    @Override
    protected Void doInBackground(SetterSubjects... params) {
        SetterSubjects setter = params[0];


        ContentValues values = new ContentValues();
        values.put(DBContract.Subjects.NAME, setter.getName());

        if (!listKeys.contains(setter.getName()))
            db.insert(DBContract.Subjects.TABLE_NAME, null, values);

        listKeys = dbOperations.getSubjectKeys(db);

        return null;
    }

}
