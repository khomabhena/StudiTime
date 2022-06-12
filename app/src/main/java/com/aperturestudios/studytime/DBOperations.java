package com.aperturestudios.studytime;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DBOperations extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1; //8
    private static final String DB_NAME = "studitime.db";
    //private String localUid;

    Context context;

    DBOperations(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_12);
        db.execSQL(QUERY_13);
        db.execSQL(QUERY_KEYWORDS);
        db.execSQL(QUERY_QUEST);
        db.execSQL(QUERY_RESPONSES);
        db.execSQL(QUERY_ACTIONS);
        db.execSQL(QUERY_ORDERED_MEALS);
        db.execSQL(QUERY_ACTIONS_ONE);
        db.execSQL(QUERY_ACTIONS_TWO);
        db.execSQL(QUERY_ACTIONS_THREE);
        db.execSQL(QUERY_ACTIONS_FOUR);
        db.execSQL(QUERY_ACTIONS_FIVE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Subjects.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Parents.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Keywords.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Timetable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Notes.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Categories.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Teacher.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Actions1.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Actions2.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Actions3.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Actions4.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Actions5.TABLE_NAME);

        onCreate(db);
    }

    Cursor getSubjects(SQLiteDatabase db) {
        Cursor cursor;

        String[] projections = {
                DBContract.Subjects.ID,
                DBContract.Subjects.NAME
        };

// + DBContract.Event.DESCRIPTION + "='" + localUid + "'"
        cursor = db.query(
                true,
                DBContract.Subjects.TABLE_NAME, projections,
                null,
                null,
                null,
                null,
                DBContract.Subjects.ID + " ASC",
                null
        );

        return cursor;
    }

    List<String> getSubjectNames(SQLiteDatabase db) {
        Cursor cursor;

        String[] arrayMax = new String[]{""};
        List<String> listKeys = new LinkedList<>(Arrays.asList(arrayMax));
        String[] projections = {DBContract.Subjects.NAME};
        cursor = db.query(true, DBContract.Subjects.TABLE_NAME, projections, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            listKeys.add(cursor.getString(cursor.getColumnIndex(DBContract.Subjects.NAME)));
        }
        cursor.close();

        return listKeys;
    }

    List<String> getSubjectKeys(SQLiteDatabase db) {
        Cursor cursor;

        String[] arrayMax = new String[]{""};
        List<String> listKeys = new LinkedList<>(Arrays.asList(arrayMax));
        String[] projections = {DBContract.Subjects.NAME};
        cursor = db.query(true, DBContract.Subjects.TABLE_NAME, projections, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            listKeys.add(cursor.getString(cursor.getColumnIndex(DBContract.Subjects.NAME)));
        }
        cursor.close();

        return listKeys;
    }

    Cursor getNotes(SQLiteDatabase db) {
        Cursor cursor;

        String[] projections = {
                DBContract.Notes.KEY,
                DBContract.Notes.PHONE_NUMBER,
                DBContract.Notes.STUDENT_NAME,
                DBContract.Notes.LOCAL_URL,
                DBContract.Notes.ONLINE_URL,
                DBContract.Notes.SUBJECT,
                DBContract.Notes.CHAPTER,
                DBContract.Notes.TOPIC,
                DBContract.Notes.TIMESTAMP
        };

// + DBContract.Event.DESCRIPTION + "='" + localUid + "'"
        cursor = db.query(
                true,
                DBContract.Notes.TABLE_NAME, projections,
                null,
                null,
                null,
                null,
                DBContract.Notes.TIMESTAMP + " DESC",
                null
        );

        return cursor;
    }

    Cursor getSearchDefault(SQLiteDatabase db) {
        Cursor cursor;

        String[] projections = {
                DBContract.Notes.KEY,
                DBContract.Notes.PHONE_NUMBER,
                DBContract.Notes.STUDENT_NAME,
                DBContract.Notes.LOCAL_URL,
                DBContract.Notes.ONLINE_URL,
                DBContract.Notes.SUBJECT,
                DBContract.Notes.CHAPTER,
                DBContract.Notes.TOPIC,
                DBContract.Notes.TIMESTAMP
        };

// + DBContract.Event.DESCRIPTION + "='" + localUid + "'"
        cursor = db.query(
                true,
                DBContract.Notes.TABLE_NAME, projections,
                null,
                null,
                DBContract.Notes.CHAPTER,
                null,
                DBContract.Notes.SUBJECT + " ASC",
                null
        );

        return cursor;
    }

    List<String> getNoteKeys(SQLiteDatabase db) {
        Cursor cursor;

        String[] arrayMax = new String[]{""};
        List<String> listKeys = new LinkedList<>(Arrays.asList(arrayMax));
        String[] projections = {DBContract.Notes.KEY};
        cursor = db.query(true, DBContract.Notes.TABLE_NAME, projections, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            listKeys.add(cursor.getString(cursor.getColumnIndex(DBContract.Notes.KEY)));
        }
        cursor.close();

        return listKeys;
    }

    Cursor getParents(SQLiteDatabase db) {
        Cursor cursor;

        String[] projections = {
                DBContract.Parents.ID,
                DBContract.Parents.NAME
        };

// + DBContract.Event.DESCRIPTION + "='" + localUid + "'"
        cursor = db.query(
                true,
                DBContract.Parents.TABLE_NAME, projections,
                null,
                null,
                null,
                null,
                DBContract.Parents.ID + " ASC",
                null
        );

        return cursor;
    }

    List<String> getParentKeys(SQLiteDatabase db) {
        Cursor cursor;

        String[] arrayMax = new String[]{""};
        List<String> listKeys = new LinkedList<>(Arrays.asList(arrayMax));
        String[] projections = {DBContract.Parents.NAME};
        cursor = db.query(true, DBContract.Parents.TABLE_NAME, projections, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            listKeys.add(cursor.getString(cursor.getColumnIndex(DBContract.Parents.NAME)));
        }
        cursor.close();

        return listKeys;
    }

    Cursor getTeachers(SQLiteDatabase db) {
        Cursor cursor;

        String[] projections = {
                DBContract.Teacher.ID,
                DBContract.Teacher.NAME
        };

// + DBContract.Event.DESCRIPTION + "='" + localUid + "'"
        cursor = db.query(
                true,
                DBContract.Teacher.TABLE_NAME, projections,
                null,
                null,
                null,
                null,
                DBContract.Teacher.ID + " ASC",
                null
        );

        return cursor;
    }

    List<String> getTeacherKeys(SQLiteDatabase db) {
        Cursor cursor;

        String[] arrayMax = new String[]{""};
        List<String> listKeys = new LinkedList<>(Arrays.asList(arrayMax));
        String[] projections = {DBContract.Teacher.NAME};
        cursor = db.query(true, DBContract.Teacher.TABLE_NAME, projections, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            listKeys.add(cursor.getString(cursor.getColumnIndex(DBContract.Teacher.NAME)));
        }
        cursor.close();

        return listKeys;
    }


    Cursor getTimetable(SQLiteDatabase db, String day) {
        Cursor cursor;

        String[] projections = {
                DBContract.Timetable.ID,
                DBContract.Timetable.SUBJECT,
                DBContract.Timetable.DAY,
                DBContract.Timetable.STARTTIME,
                DBContract.Timetable.ENDTIME
        };

// + DBContract.Event.DESCRIPTION + "='" + localUid + "'"
        cursor = db.query(
                true,
                DBContract.Timetable.TABLE_NAME, projections,
                DBContract.Timetable.DAY + "='" + day + "'",
                null,
                null,
                null,
                DBContract.Timetable.STARTTIME + " ASC",
                null
        );

        return cursor;
    }

    Cursor getTimetable(SQLiteDatabase db) {
        Cursor cursor;

        String[] projections = {
                DBContract.Timetable.ID,
                DBContract.Timetable.SUBJECT,
                DBContract.Timetable.DAY,
                DBContract.Timetable.STARTTIME,
                DBContract.Timetable.ENDTIME
        };

// + DBContract.Event.DESCRIPTION + "='" + localUid + "'"
        cursor = db.query(
                true,
                DBContract.Timetable.TABLE_NAME, projections,
                null,
                null,
                null,
                null,
                DBContract.Timetable.STARTTIME + " ASC",
                null
        );

        return cursor;
    }






    Cursor getDeli(SQLiteDatabase db) {
        Cursor cursor;

        String[] projections = {
                DBContract.Subjects.ID,
                DBContract.Subjects.KEY,
                DBContract.Subjects.AREA,
                DBContract.Subjects.DESCRIPTION,
                DBContract.Subjects.CHARGE,
                DBContract.Subjects.IS_AVAILABLE,
                DBContract.Subjects.IS_CLUB,
                DBContract.Subjects.POSITION
        };

// + DBContract.Event.DESCRIPTION + "='" + localUid + "'"
        cursor = db.query(
                true,
                DBContract.Subjects.TABLE_NAME, projections,
                null,
                null,
                DBContract.Subjects.KEY,
                null,
                DBContract.Subjects.POSITION + " ASC",
                null
        );

        return cursor;
    }

    List<String> getDeliveryAreaKeys(SQLiteDatabase db) {
        Cursor cursor;

        String[] arrayMax = new String[]{""};
        List<String> listKeys = new LinkedList<>(Arrays.asList(arrayMax));
        String[] projections = {DBContract.Subjects.KEY};
        cursor = db.query(true, DBContract.Subjects.TABLE_NAME, projections, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            listKeys.add(cursor.getString(cursor.getColumnIndex(DBContract.Subjects.KEY)));
        }
        cursor.close();

        return listKeys;
    }



    Cursor getOrders(SQLiteDatabase db, String uid) {
        Cursor cursor;

        String[] projections = {
                DBContract.Timetable.ID,
                DBContract.Timetable.UID,
                DBContract.Timetable.SUBJECT,
                DBContract.Timetable.STARTTIME,
                DBContract.Timetable.NOTES,
                DBContract.Timetable.ENDTIME,
                DBContract.Timetable.SURNAME,
                DBContract.Timetable.DAY,
                DBContract.Timetable.ADDRESS,
                DBContract.Timetable.ORDER_CHARGE,
                DBContract.Timetable.DELIVERY_CHARGE,
                DBContract.Timetable.PROCESSING_CHARGE,
                DBContract.Timetable.SERVICE_CHARGE,
                DBContract.Timetable.TIMESTAMP,
                DBContract.Timetable.DELIVERY_START,
                DBContract.Timetable.DELIVER_BEFORE,
                DBContract.Timetable.DELIVERED_ON,
                DBContract.Timetable.IS_PAID,
                DBContract.Timetable.IS_PREPARING,
                DBContract.Timetable.IS_CLUB,
                DBContract.Timetable.IS_DONE,
                DBContract.Timetable.IS_DELIVERED,
                DBContract.Timetable.IS_TAKEAWAY,
                DBContract.Timetable.TAKEAWAY_CHARGE,
                DBContract.Timetable.DELIVERY_AREA_KEY
        };

        cursor = db.query(
                true,
                DBContract.Timetable.TABLE_NAME, projections,
                DBContract.Timetable.UID + "='" + uid + "'",
                null,
                DBContract.Timetable.STARTTIME,
                null,
                DBContract.Timetable.TIMESTAMP + " DESC",
                null
        );

        return cursor;
    }

    List<String> getOrderKeys(SQLiteDatabase db) {
        Cursor cursor;

        String[] arrayMax = new String[]{""};
        List<String> listKeys = new LinkedList<>(Arrays.asList(arrayMax));
        String[] projections = {DBContract.Timetable.STARTTIME};
        cursor = db.query(true, DBContract.Timetable.TABLE_NAME, projections, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            listKeys.add(cursor.getString(cursor.getColumnIndex(DBContract.Timetable.STARTTIME)));
        }
        cursor.close();

        return listKeys;
    }



    Cursor getMeals(SQLiteDatabase db, String type) {
        Cursor cursor;

        String[] projections = {
                DBContract.Parents.ID,
                DBContract.Parents.CATEGORY_KEY,
                DBContract.Parents.KEY,
                DBContract.Parents.NAME,
                DBContract.Parents.SIZE,
                DBContract.Parents.LIMIT,
                DBContract.Parents.LINK,
                DBContract.Parents.PRICE,
                DBContract.Parents.DESCRIPTION,
                DBContract.Parents.IS_AVAILABLE,
                DBContract.Parents.IS_HOME_DELIVERY,
                DBContract.Parents.TIMESTAMP,
                DBContract.Parents.TAKEAWAY_CHARGE
        };

        cursor = db.query(
                true,
                DBContract.Parents.TABLE_NAME, projections,
                DBContract.Parents.CATEGORY_KEY + "='" + type + "'",
                null,
                DBContract.Parents.KEY,
                null,
                DBContract.Parents.TIMESTAMP + " ASC",
                null
        );

        return cursor;
    }

    String getOrderedMeals(SQLiteDatabase db, String key) {
        Cursor cursor;

        String[] projections = {
                DBContract.Parents.ID
        };

        cursor = db.query(
                true,
                DBContract.Parents.TABLE_NAME, projections,
                DBContract.Teacher.ORDER_KEY + "='" + key + "'",
                null,
                DBContract.Parents.KEY,
                null,
                DBContract.Parents.TIMESTAMP + " ASC",
                null
        );

        return String.valueOf(cursor.getCount());
    }

    List<String> getMealKeys(SQLiteDatabase db) {
        Cursor cursor;

        String[] arrayMax = new String[]{""};
        List<String> listKeys = new LinkedList<>(Arrays.asList(arrayMax));
        String[] projections = {DBContract.Parents.KEY};
        cursor = db.query(true, DBContract.Parents.TABLE_NAME, projections, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            listKeys.add(cursor.getString(cursor.getColumnIndex(DBContract.Parents.KEY)));
        }
        cursor.close();

        return listKeys;
    }

    String getFirstCategory(SQLiteDatabase db) {
        Cursor cursor;
        String key = "";

        String[] projections = {DBContract.Categories.KEY};
        cursor = db.query(true, DBContract.Categories.TABLE_NAME, projections,
                DBContract.Categories.IS_AVAILABLE + "='yes' ",
                null,
                null, null, DBContract.Categories.POSITION + " DESC", null);

        while (cursor.moveToNext()) {
            key = cursor.getString(cursor.getColumnIndex(DBContract.Categories.KEY));
        }
        cursor.close();

        return key;
    }



    Cursor getDeliveryTime(SQLiteDatabase db) {
        Cursor cursor;

        String[] projections = {
                DBContract.Notes.ID,
                DBContract.Notes.KEY,
                DBContract.Notes.CHAPTER,
                DBContract.Notes.PHONE_NUMBER,
                DBContract.Notes.STUDENT_NAME,
                DBContract.Notes.LOCAL_URL,
                DBContract.Notes.ONLINE_URL,
                DBContract.Notes.TOPIC,
                DBContract.Notes.SUBJECT
        };

        cursor = db.query(
                true,
                DBContract.Notes.TABLE_NAME, projections,
                null,
                null,
                DBContract.Notes.KEY,
                null,
                DBContract.Notes.TOPIC + " ASC",
                null
        );

        return cursor;
    }

    List<String> getDeliveryTimeKeys(SQLiteDatabase db) {
        Cursor cursor;

        String[] arrayMax = new String[]{""};
        List<String> listKeys = new LinkedList<>(Arrays.asList(arrayMax));
        String[] projections = {DBContract.Notes.KEY};
        cursor = db.query(true, DBContract.Notes.TABLE_NAME, projections, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            listKeys.add(cursor.getString(cursor.getColumnIndex(DBContract.Notes.KEY)));
        }
        cursor.close();

        return listKeys;
    }



    Cursor getKeywords(SQLiteDatabase db, String language, String type) {
        Cursor cursor;
        String[] projections = {
                DBContract.Keywords.ID,
                DBContract.Keywords.KEY,
                DBContract.Keywords.NAME,
                DBContract.Keywords.TYPE,
                DBContract.Keywords.ENABLED
        };

        cursor = db.query(
                true,
                DBContract.Keywords.TABLE_NAME, projections,
                DBContract.Keywords.LANGUAGE + "='" + language + "' AND " +
                        DBContract.Keywords.TYPE + "='" + type + "' ",
                null,
                DBContract.Keywords.KEY,
                null,
                DBContract.Keywords.ID + " ASC",
                null
        );

        return cursor;
    }

    List<String> getKeywordsKeys(SQLiteDatabase db) {
        Cursor cursor;

        String[] arrayMax = new String[]{""};
        List<String> listKeys = new LinkedList<>(Arrays.asList(arrayMax));
        String[] projections = {DBContract.Keywords.KEY};
        cursor = db.query(true, DBContract.Keywords.TABLE_NAME, projections, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            listKeys.add(cursor.getString(cursor.getColumnIndex(DBContract.Keywords.KEY)));
        }
        cursor.close();

        return listKeys;
    }



    Cursor getChats(SQLiteDatabase db, String language) {
        Cursor cursor;
        String[] projections = {
                DBContract.Parents.ID,
                DBContract.Parents.KEY,
                DBContract.Parents.NAME,
                DBContract.Parents.LINK,
                DBContract.Parents.PRICE,
                DBContract.Parents.IS_AVAILABLE,
                DBContract.Parents.DESCRIPTION
        };
// + DBContract.Event.DESCRIPTION + "='" + localUid + "'"
        cursor = db.query(
                true,
                DBContract.Parents.TABLE_NAME, projections,
                DBContract.Parents.TIMESTAMP + "='" + language + "'",
                null,
                DBContract.Parents.ID,
                null,
                DBContract.Parents.DESCRIPTION + " ASC",
                null
        );

        return cursor;
    }



    private static final String QUERY_12 = "CREATE TABLE "+ DBContract.Subjects.TABLE_NAME +"("+
            DBContract.Subjects.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBContract.Subjects.NAME + " TEXT, " +
            DBContract.Subjects.IS_SEEN + " TEXT, " +
            DBContract.Subjects.IS_MALE + " TEXT, " +
            DBContract.Subjects.KEY + " TEXT, " +
            DBContract.Subjects.AREA + " TEXT, " +
            DBContract.Subjects.DESCRIPTION + " TEXT, " +
            DBContract.Subjects.CHARGE + " TEXT, " +
            DBContract.Subjects.IS_AVAILABLE + " TEXT, " +
            DBContract.Subjects.IS_CLUB + " TEXT, " +
            DBContract.Subjects.POSITION + " TEXT, " +
            DBContract.Subjects.MEDICAL_AID + " TEXT, " +
            DBContract.Subjects.EMAIL + " TEXT, " +
            DBContract.Subjects.SUFFIX + " TEXT, " +
            DBContract.Subjects.NO + " TEXT, " +
            DBContract.Subjects.ADDRESS + " TEXT, " +
            DBContract.Subjects.EMPLOYER + " TEXT, " +
            DBContract.Subjects.PHONE + " TEXT, " +
            DBContract.Subjects.SPECIMEN_TYPE + " TEXT, " +
            DBContract.Subjects.MEDICAL_LINK + " TEXT, " +
            DBContract.Subjects.FORM_LINK + " TEXT, " +
            DBContract.Subjects.PATIENT_DOB +" TEXT);";

    private static final String QUERY_13 = "CREATE TABLE "+ DBContract.Parents.TABLE_NAME +"("+
            DBContract.Parents.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBContract.Parents.TYPE + " TEXT, " +
            DBContract.Parents.KEY + " TEXT, " +
            DBContract.Parents.NAME + " TEXT, " +
            DBContract.Parents.LINK + " TEXT, " +
            DBContract.Parents.PRICE + " TEXT, " +
            DBContract.Parents.IS_AVAILABLE + " TEXT, " +
            DBContract.Parents.DESCRIPTION + " TEXT, " +
            DBContract.Parents.TIMESTAMP + " TEXT, " +
            DBContract.Parents.TAKEAWAY_CHARGE + " TEXT, " +
            DBContract.Parents.LIMIT + " TEXT, " +
            DBContract.Parents.SIZE + " TEXT, " +
            DBContract.Parents.IS_HOME_DELIVERY + " TEXT, " +
            DBContract.Parents.CATEGORY_KEY + " TEXT, " +
            DBContract.Parents.COl_13 + " TEXT, " +
            DBContract.Parents.COl_14 + " TEXT, " +
            DBContract.Parents.COl_15 + " TEXT, " +
            DBContract.Parents.COl_16 + " TEXT, " +
            DBContract.Parents.COl_17 + " TEXT, " +
            DBContract.Parents.COl_18 + " TEXT, " +
            DBContract.Parents.COl_19 + " TEXT, " +
            DBContract.Parents.COl_20 +" TEXT);";

    private static final String QUERY_ORDERED_MEALS = "CREATE TABLE "+ DBContract.Teacher.TABLE_NAME +"("+
            DBContract.Teacher.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBContract.Teacher.TYPE + " TEXT, " +
            DBContract.Teacher.KEY + " TEXT, " +
            DBContract.Teacher.NAME + " TEXT, " +
            DBContract.Teacher.LINK + " TEXT, " +
            DBContract.Teacher.PRICE + " TEXT, " +
            DBContract.Teacher.IS_AVAILABLE + " TEXT, " +
            DBContract.Teacher.DISCOUNT + " TEXT, " +
            DBContract.Teacher.TIMESTAMP + " TEXT, " +
            DBContract.Teacher.TAKEAWAY_CHARGE + " TEXT, " +
            DBContract.Teacher.ORDER_KEY + " TEXT, " +
            DBContract.Teacher.LIMIT + " TEXT, " +
            DBContract.Teacher.SIZE + " TEXT, " +
            DBContract.Teacher.DESCRIPTION + " TEXT, " +
            DBContract.Teacher.IS_HOME_DELIVERY + " TEXT, " +
            DBContract.Teacher.CATEGORY_KEY + " TEXT, " +
            DBContract.Teacher.COl_15 + " TEXT, " +
            DBContract.Teacher.COl_16 + " TEXT, " +
            DBContract.Teacher.COl_17 + " TEXT, " +
            DBContract.Teacher.COl_18 + " TEXT, " +
            DBContract.Teacher.COl_19 + " TEXT, " +
            DBContract.Teacher.COl_20 +" TEXT);";

    private static final String QUERY_QUEST = "CREATE TABLE "+ DBContract.Timetable.TABLE_NAME +"("+
            DBContract.Timetable.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBContract.Timetable.UID + " TEXT, " +
            DBContract.Timetable.SUBJECT + " TEXT, " +
            DBContract.Timetable.STARTTIME + " TEXT, " +
            DBContract.Timetable.NOTES + " TEXT, " +
            DBContract.Timetable.DELIVERY_AREA_KEY + " TEXT, " +
            DBContract.Timetable.ENDTIME + " TEXT, " +
            DBContract.Timetable.SURNAME + " TEXT, " +
            DBContract.Timetable.DAY + " TEXT, " +
            DBContract.Timetable.ADDRESS + " TEXT, " +
            DBContract.Timetable.ORDER_CHARGE + " TEXT, " +
            DBContract.Timetable.DELIVERY_CHARGE + " TEXT, " +
            DBContract.Timetable.PROCESSING_CHARGE + " TEXT, " +
            DBContract.Timetable.SERVICE_CHARGE + " TEXT, " +
            DBContract.Timetable.TIMESTAMP + " TEXT, " +
            DBContract.Timetable.DELIVER_BEFORE + " TEXT, " +
            DBContract.Timetable.DELIVERED_ON + " TEXT, " +
            DBContract.Timetable.IS_PAID + " TEXT, " +
            DBContract.Timetable.IS_PREPARING + " TEXT, " +
            DBContract.Timetable.IS_CLUB + " TEXT, " +
            DBContract.Timetable.IS_DONE + " TEXT, " +
            DBContract.Timetable.IS_DELIVERED + " TEXT, " +
            DBContract.Timetable.IS_TAKEAWAY + " TEXT, " +
            DBContract.Timetable.TAKEAWAY_CHARGE + " TEXT, " +
            DBContract.Timetable.DELIVERY_START +" TEXT);";

    private static final String QUERY_RESPONSES = "CREATE TABLE "+ DBContract.Notes.TABLE_NAME +"("+
            DBContract.Notes.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBContract.Notes.LOCAL_UID + " TEXT, " +
            DBContract.Notes.KEY + " TEXT, " +
            DBContract.Notes.PHONE_NUMBER + " TEXT, " +
            DBContract.Notes.STUDENT_NAME + " TEXT, " +
            DBContract.Notes.LOCAL_URL + " TEXT, " +
            DBContract.Notes.ONLINE_URL + " TEXT, " +
            DBContract.Notes.SUBJECT + " TEXT, " +
            DBContract.Notes.CHAPTER + " TEXT, " +
            DBContract.Notes.TOPIC + " TEXT, " +
            DBContract.Notes.TIMESTAMP + " TEXT, " +
            DBContract.Notes.COl_10 + " TEXT, " +
            DBContract.Notes.COl_11 + " TEXT, " +
            DBContract.Notes.COl_12 + " TEXT, " +
            DBContract.Notes.COl_13 + " TEXT, " +
            DBContract.Notes.COl_14 + " TEXT, " +
            DBContract.Notes.COl_15 + " TEXT, " +
            DBContract.Notes.COl_16 + " TEXT, " +
            DBContract.Timetable.IS_DELIVERED + " TEXT, " +
            DBContract.Notes.COl_18 + " TEXT, " +
            DBContract.Notes.COl_19 + " TEXT, " +
            DBContract.Notes.COl_20 +" TEXT);";

    private static final String QUERY_ACTIONS = "CREATE TABLE "+ DBContract.Categories.TABLE_NAME +"("+
            DBContract.Categories.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBContract.Categories.LOCAL_UID + " TEXT, " +
            DBContract.Categories.KEY + " TEXT, " +
            DBContract.Categories.PARENT_KEY + " TEXT, " +
            DBContract.Categories.NAME + " TEXT, " +
            DBContract.Categories.POSITION + " TEXT, " +
            DBContract.Categories.ENABLED + " TEXT, " +
            DBContract.Categories.TIMESTAMP + " TEXT, " +
            DBContract.Categories.IS_AVAILABLE + " TEXT, " +
            DBContract.Categories.COl_8 + " TEXT, " +
            DBContract.Categories.COl_9 + " TEXT, " +
            DBContract.Categories.COl_10 + " TEXT, " +
            DBContract.Categories.COl_11 + " TEXT, " +
            DBContract.Categories.COl_12 + " TEXT, " +
            DBContract.Categories.COl_13 + " TEXT, " +
            DBContract.Categories.COl_14 + " TEXT, " +
            DBContract.Categories.COl_15 + " TEXT, " +
            DBContract.Categories.COl_16 + " TEXT, " +
            DBContract.Categories.COl_17 + " TEXT, " +
            DBContract.Categories.COl_18 + " TEXT, " +
            DBContract.Categories.COl_19 + " TEXT, " +
            DBContract.Categories.COl_20 +" TEXT);";

    private static final String QUERY_KEYWORDS = "CREATE TABLE "+ DBContract.Keywords.TABLE_NAME +"("+
            DBContract.Keywords.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBContract.Keywords.LOCAL_UID + " TEXT, " +
            DBContract.Keywords.KEY + " TEXT, " +
            DBContract.Keywords.NAME + " TEXT, " +
            DBContract.Keywords.TYPE + " TEXT, " +
            DBContract.Keywords.ENABLED + " TEXT, " +
            DBContract.Keywords.QUESTION + " TEXT, " +
            DBContract.Keywords.TIMESTAMP + " TEXT, " +
            DBContract.Keywords.LANGUAGE + " TEXT, " +
            DBContract.Keywords.COl_8 + " TEXT, " +
            DBContract.Keywords.COl_9 + " TEXT, " +
            DBContract.Keywords.COl_10 + " TEXT, " +
            DBContract.Keywords.COl_11 + " TEXT, " +
            DBContract.Keywords.COl_12 + " TEXT, " +
            DBContract.Keywords.COl_13 + " TEXT, " +
            DBContract.Keywords.COl_14 + " TEXT, " +
            DBContract.Keywords.COl_15 + " TEXT, " +
            DBContract.Keywords.COl_16 + " TEXT, " +
            DBContract.Keywords.COl_17 + " TEXT, " +
            DBContract.Keywords.COl_18 + " TEXT, " +
            DBContract.Keywords.COl_19 + " TEXT, " +
            DBContract.Keywords.COl_20 +" TEXT);";

    private static final String QUERY_ACTIONS_ONE = "CREATE TABLE "+ DBContract.Actions1.TABLE_NAME +"("+
            DBContract.Categories.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBContract.Categories.LOCAL_UID + " TEXT, " +
            DBContract.Categories.KEY + " TEXT, " +
            DBContract.Categories.PARENT_KEY + " TEXT, " +
            DBContract.Categories.NAME + " TEXT, " +
            DBContract.Categories.POSITION + " TEXT, " +
            DBContract.Categories.ENABLED + " TEXT, " +
            DBContract.Categories.TIMESTAMP + " TEXT, " +
            DBContract.Categories.IS_AVAILABLE + " TEXT, " +
            DBContract.Categories.COl_8 + " TEXT, " +
            DBContract.Categories.COl_9 + " TEXT, " +
            DBContract.Categories.COl_10 + " TEXT, " +
            DBContract.Categories.COl_11 + " TEXT, " +
            DBContract.Categories.COl_12 + " TEXT, " +
            DBContract.Categories.COl_13 + " TEXT, " +
            DBContract.Categories.COl_14 + " TEXT, " +
            DBContract.Categories.COl_15 + " TEXT, " +
            DBContract.Categories.COl_16 + " TEXT, " +
            DBContract.Categories.COl_17 + " TEXT, " +
            DBContract.Categories.COl_18 + " TEXT, " +
            DBContract.Categories.COl_19 + " TEXT, " +
            DBContract.Categories.COl_20 +" TEXT);";

    private static final String QUERY_ACTIONS_TWO = "CREATE TABLE "+ DBContract.Actions2.TABLE_NAME +"("+
            DBContract.Categories.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBContract.Categories.LOCAL_UID + " TEXT, " +
            DBContract.Categories.KEY + " TEXT, " +
            DBContract.Categories.PARENT_KEY + " TEXT, " +
            DBContract.Categories.NAME + " TEXT, " +
            DBContract.Categories.POSITION + " TEXT, " +
            DBContract.Categories.ENABLED + " TEXT, " +
            DBContract.Categories.TIMESTAMP + " TEXT, " +
            DBContract.Categories.IS_AVAILABLE + " TEXT, " +
            DBContract.Categories.COl_8 + " TEXT, " +
            DBContract.Categories.COl_9 + " TEXT, " +
            DBContract.Categories.COl_10 + " TEXT, " +
            DBContract.Categories.COl_11 + " TEXT, " +
            DBContract.Categories.COl_12 + " TEXT, " +
            DBContract.Categories.COl_13 + " TEXT, " +
            DBContract.Categories.COl_14 + " TEXT, " +
            DBContract.Categories.COl_15 + " TEXT, " +
            DBContract.Categories.COl_16 + " TEXT, " +
            DBContract.Categories.COl_17 + " TEXT, " +
            DBContract.Categories.COl_18 + " TEXT, " +
            DBContract.Categories.COl_19 + " TEXT, " +
            DBContract.Categories.COl_20 +" TEXT);";

    private static final String QUERY_ACTIONS_THREE = "CREATE TABLE "+ DBContract.Actions3.TABLE_NAME +"("+
            DBContract.Categories.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBContract.Categories.LOCAL_UID + " TEXT, " +
            DBContract.Categories.KEY + " TEXT, " +
            DBContract.Categories.PARENT_KEY + " TEXT, " +
            DBContract.Categories.NAME + " TEXT, " +
            DBContract.Categories.POSITION + " TEXT, " +
            DBContract.Categories.ENABLED + " TEXT, " +
            DBContract.Categories.TIMESTAMP + " TEXT, " +
            DBContract.Categories.IS_AVAILABLE + " TEXT, " +
            DBContract.Categories.COl_8 + " TEXT, " +
            DBContract.Categories.COl_9 + " TEXT, " +
            DBContract.Categories.COl_10 + " TEXT, " +
            DBContract.Categories.COl_11 + " TEXT, " +
            DBContract.Categories.COl_12 + " TEXT, " +
            DBContract.Categories.COl_13 + " TEXT, " +
            DBContract.Categories.COl_14 + " TEXT, " +
            DBContract.Categories.COl_15 + " TEXT, " +
            DBContract.Categories.COl_16 + " TEXT, " +
            DBContract.Categories.COl_17 + " TEXT, " +
            DBContract.Categories.COl_18 + " TEXT, " +
            DBContract.Categories.COl_19 + " TEXT, " +
            DBContract.Categories.COl_20 +" TEXT);";

    private static final String QUERY_ACTIONS_FOUR = "CREATE TABLE "+ DBContract.Actions4.TABLE_NAME +"("+
            DBContract.Categories.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBContract.Categories.LOCAL_UID + " TEXT, " +
            DBContract.Categories.KEY + " TEXT, " +
            DBContract.Categories.PARENT_KEY + " TEXT, " +
            DBContract.Categories.NAME + " TEXT, " +
            DBContract.Categories.POSITION + " TEXT, " +
            DBContract.Categories.ENABLED + " TEXT, " +
            DBContract.Categories.TIMESTAMP + " TEXT, " +
            DBContract.Categories.IS_AVAILABLE + " TEXT, " +
            DBContract.Categories.COl_8 + " TEXT, " +
            DBContract.Categories.COl_9 + " TEXT, " +
            DBContract.Categories.COl_10 + " TEXT, " +
            DBContract.Categories.COl_11 + " TEXT, " +
            DBContract.Categories.COl_12 + " TEXT, " +
            DBContract.Categories.COl_13 + " TEXT, " +
            DBContract.Categories.COl_14 + " TEXT, " +
            DBContract.Categories.COl_15 + " TEXT, " +
            DBContract.Categories.COl_16 + " TEXT, " +
            DBContract.Categories.COl_17 + " TEXT, " +
            DBContract.Categories.COl_18 + " TEXT, " +
            DBContract.Categories.COl_19 + " TEXT, " +
            DBContract.Categories.COl_20 +" TEXT);";

    private static final String QUERY_ACTIONS_FIVE = "CREATE TABLE "+ DBContract.Actions5.TABLE_NAME +"("+
            DBContract.Categories.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBContract.Categories.LOCAL_UID + " TEXT, " +
            DBContract.Categories.KEY + " TEXT, " +
            DBContract.Categories.PARENT_KEY + " TEXT, " +
            DBContract.Categories.NAME + " TEXT, " +
            DBContract.Categories.POSITION + " TEXT, " +
            DBContract.Categories.ENABLED + " TEXT, " +
            DBContract.Categories.TIMESTAMP + " TEXT, " +
            DBContract.Categories.IS_AVAILABLE + " TEXT, " +
            DBContract.Categories.COl_8 + " TEXT, " +
            DBContract.Categories.COl_9 + " TEXT, " +
            DBContract.Categories.COl_10 + " TEXT, " +
            DBContract.Categories.COl_11 + " TEXT, " +
            DBContract.Categories.COl_12 + " TEXT, " +
            DBContract.Categories.COl_13 + " TEXT, " +
            DBContract.Categories.COl_14 + " TEXT, " +
            DBContract.Categories.COl_15 + " TEXT, " +
            DBContract.Categories.COl_16 + " TEXT, " +
            DBContract.Categories.COl_17 + " TEXT, " +
            DBContract.Categories.COl_18 + " TEXT, " +
            DBContract.Categories.COl_19 + " TEXT, " +
            DBContract.Categories.COl_20 +" TEXT);";


}