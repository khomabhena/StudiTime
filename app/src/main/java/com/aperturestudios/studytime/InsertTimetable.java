package com.aperturestudios.studytime;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.List;

public class InsertTimetable extends AsyncTask<SetterTimetable, Void, Void> {

    private SQLiteDatabase db;
    DBOperations dbOperations;
    List listKeys;

    public InsertTimetable(Context context, List listKeys) {
        dbOperations = new DBOperations(context);
        db = dbOperations.getWritableDatabase();
        this.listKeys = listKeys;
    }

    @Override
    protected Void doInBackground(SetterTimetable... params) {
        SetterTimetable setter = params[0];


        ContentValues values = new ContentValues();
        values.put(DBContract.Timetable.SUBJECT, setter.getSubject());
        values.put(DBContract.Timetable.STARTTIME, setter.getStartTime());
        values.put(DBContract.Timetable.ENDTIME, setter.getEndTime());
        values.put(DBContract.Timetable.DAY, setter.getDay());

        //if (!listKeys.contains(setter.getName()))
            db.insert(DBContract.Timetable.TABLE_NAME, null, values);

        listKeys = dbOperations.getParentKeys(db);

        return null;
    }

}
