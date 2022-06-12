package com.aperturestudios.studytime;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SubjectList extends AppCompatActivity {

    Context context;
    XConversions xConversions;
    RecyclerView recyclerView;
    CardView cardStart, cardEnd;
    TextView tvStartTime, tvEndTime;

    DBOperations dbOperations;
    SQLiteDatabase db;
    List list;
    List<String> listKeys;
    Adapter adapter;


    int hourX, minuteX, hourY, minuteY;
    static final int xTIME_DIALOG_ID = 282;
    static final int yTIME_DIALOG_ID = 543;
    private static long StartStamp = 0;
    private static long EndStamp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        xConversions = new XConversions(context);
        dbOperations = new DBOperations(context);
        db = dbOperations.getWritableDatabase();
        list = new ArrayList();
        listKeys = dbOperations.getSubjectKeys(db);
        recyclerView = findViewById(R.id.recyclerView);
        cardStart = findViewById(R.id.cardStart);
        cardEnd = findViewById(R.id.cardEnd);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvEndTime = findViewById(R.id.tvEndTime);

        xConversions.initializeSingleRecyclerviewLayouts(recyclerView, LinearLayoutManager.VERTICAL);
        new BackTask().execute();

        Calendar calendar = Calendar.getInstance();
        hourX = calendar.get(Calendar.HOUR_OF_DAY); hourY = calendar.get(Calendar.HOUR_OF_DAY);
        minuteX = calendar.get(Calendar.MINUTE); minuteY = calendar.get(Calendar.MINUTE);

        cardStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFancyDateTimePicker(xTIME_DIALOG_ID).show();
            }
        });
        cardEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFancyDateTimePicker(yTIME_DIALOG_ID).show();
            }
        });
    }

    private class BackTask extends AsyncTask<Void, SetterSubjects, Integer> {

        List listInternal;

        @Override
        protected Integer doInBackground(Void... params) {

            Cursor cursor = dbOperations.getSubjects(db);
            int count = cursor.getCount();

            String name;

            listInternal = new ArrayList();
            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex(DBContract.Subjects.NAME));

                SetterSubjects setter = new SetterSubjects(name);

                publishProgress(setter);
            }

            return count;
        }

        @Override
        protected void onProgressUpdate(SetterSubjects... values) {
            listInternal.add(values[0]);
        }

        @Override
        protected void onPostExecute(Integer count) {
            adapter = new Adapter(listInternal);
            recyclerView.setAdapter(adapter);
        }
    }

    class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

        private List listAdapter;

        public Adapter(List listAdapter) {
            this.listAdapter = listAdapter;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            int layoutIdForListItem = R.layout.row_subjects;
            LayoutInflater inflater = LayoutInflater.from(context);
            boolean shouldAttachToParentImmediately = false;

            View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
            Holder holder = new Holder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.bind((SetterSubjects) listAdapter.get(position));
        }

        @Override
        public int getItemCount() {
            return listAdapter.size();
        }

        class Holder extends RecyclerView.ViewHolder {
            TextView tvName;
            ImageView ivDelete;

            public Holder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvName);
                ivDelete = itemView.findViewById(R.id.ivDelete);
            }

            void bind(final SetterSubjects setter) {
                tvName.setText(setter.getName());
                ivDelete.setVisibility(View.GONE);
                tvName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetterTimetable setterTimetable = new SetterTimetable(0, setter.getName(), Timetable.day, StartStamp, EndStamp);
                        new InsertTimetable(context, listKeys).execute(setterTimetable);
                        finish();
                    }
                });
            }
        }

    }



    protected Dialog createFancyDateTimePicker(int id) {
        Calendar calendar = Calendar.getInstance();
        switch (id) {
            case xTIME_DIALOG_ID:
                hourX = calendar.get(Calendar.HOUR_OF_DAY);
                minuteX = calendar.get(Calendar.MINUTE);
                return new TimePickerDialog(this, xTimeSetListener, hourX, minuteX, true);

            case yTIME_DIALOG_ID:
                hourY = calendar.get(Calendar.HOUR_OF_DAY);
                minuteY = calendar.get(Calendar.MINUTE);
                return new TimePickerDialog(this, yTimeSetListener, hourY, minuteY, true);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener xTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hourX = hourOfDay;
            //hourY = hourOfDay + 5;
            minuteX = minute;
            String builder = getTheValue(hourX) + ":" + getTheValue(minuteX);
            tvStartTime.setText(builder);

            checkTimeDateValues("x");
        }
    };

    private TimePickerDialog.OnTimeSetListener yTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hourY = hourOfDay;
            minuteY = minute;
            String builder = getTheValue(hourY) + ":" + getTheValue(minuteY);
            tvEndTime.setText(builder);

            checkTimeDateValues("y");
        }
    };

    public void checkTimeDateValues(String source){
        if(source.equals("x")) {
            if (!tvStartTime.getText().toString().equals("")) {
                generateTimestamp("x", hourX, minuteX);
            }
        }
        else if(source.equals("y")) {
            if (!tvEndTime.getText().toString().equals("")) {
                generateTimestamp("y", hourY, minuteY);
            }
        }
    }

    public void generateTimestamp(String source, int hour, int minute){
        long timestamp;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        timestamp = c.getTimeInMillis();

        if(source.equals("x")){
            StartStamp = timestamp;
        } else if (source.equals("y")){
            EndStamp = timestamp;
        }
    }

    public long getCurrentTimestamp(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        return calendar.getTimeInMillis();
    }

    public String getTheValue(int value){
        String theValue = String.valueOf(value);
        if (theValue.length() == 1){
            return "0"+theValue;
        } else {
            return theValue;
        }
    }

}
