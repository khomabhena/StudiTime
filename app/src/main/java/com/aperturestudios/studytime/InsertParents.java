package com.aperturestudios.studytime;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.List;

public class InsertParents extends AsyncTask<SetterParents, Void, Void> {

    private SQLiteDatabase db;
    DBOperations dbOperations;
    List listKeys;

    public InsertParents(Context context, List listKeys) {
        dbOperations = new DBOperations(context);
        db = dbOperations.getWritableDatabase();
        this.listKeys = listKeys;
    }

    @Override
    protected Void doInBackground(SetterParents... params) {
        SetterParents setter = params[0];


        ContentValues values = new ContentValues();
        values.put(DBContract.Parents.NAME, setter.getName());

        if (!listKeys.contains(setter.getName()))
            db.insert(DBContract.Parents.TABLE_NAME, null, values);

        listKeys = dbOperations.getParentKeys(db);

        return null;
    }

}
