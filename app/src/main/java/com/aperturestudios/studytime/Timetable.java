package com.aperturestudios.studytime;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Timetable extends AppCompatActivity {

    Context context;
    XConversions xConversions;
    FloatingActionButton fabAdd;
    RecyclerView recyclerView;
    CardView sunday, monday, tuesday, wednesday, thursday, friday, saturday;
    static String day = "Sun";

    DBOperations dbOperations;
    SQLiteDatabase db;
    List list;
    List<String> listKeys;
    Adapter adapter;

    String[] cardIds = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    CardView[] cards = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        xConversions = new XConversions(context);
        dbOperations = new DBOperations(context);
        db = dbOperations.getWritableDatabase();
        list = new ArrayList();
        listKeys = dbOperations.getTeacherKeys(db);
        fabAdd = findViewById(R.id.fabAdd);
        recyclerView = findViewById(R.id.recyclerView);
        sunday = findViewById(R.id.sunday);
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        thursday = findViewById(R.id.thursday);
        friday = findViewById(R.id.friday);
        saturday = findViewById(R.id.saturday);

        cards = new CardView[]{sunday, monday, tuesday, wednesday, thursday, friday, saturday};
        xConversions.initializeSingleRecyclerviewLayouts(recyclerView, LinearLayoutManager.VERTICAL);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SubjectList.class));
            }
        });
        new BackTask().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new BackTask().execute();
    }


    private void disableEveryColor(String id) {
        for (int x = 0; x < cardIds.length; x++) {
            if (cardIds[x].equals(id))
                cards[x].setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
            else
                cards[x].setCardBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    public void sun(View view) {
        day = "Sun";
        disableEveryColor(cardIds[0]);
        new BackTask().execute();
    }

    public void mon(View view) {
        day = "Mon";
        disableEveryColor(cardIds[1]);
        new BackTask().execute();
    }

    public void tue(View view) {
        day = "Tue";
        disableEveryColor(cardIds[2]);
        new BackTask().execute();
    }

    public void wed(View view) {
        day = "Wed";
        disableEveryColor(cardIds[3]);
        new BackTask().execute();
    }

    public void thu(View view) {
        day = "Thu";
        disableEveryColor(cardIds[4]);
        new BackTask().execute();
    }

    public void fri(View view) {
        day = "Fri";
        disableEveryColor(cardIds[5]);
        new BackTask().execute();
    }

    public void sat(View view) {
        day = "Sat";
        disableEveryColor(cardIds[6]);
        new BackTask().execute();
    }

    private class BackTask extends AsyncTask<Void, SetterTimetable, Integer> {

        List listInternal;

        @Override
        protected Integer doInBackground(Void... params) {

            Cursor cursor = dbOperations.getTimetable(db, day);
            int count = cursor.getCount();

            int id;
            String subject, day;
            long start, end;

            listInternal = new ArrayList();
            while (cursor.moveToNext()) {
                id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBContract.Timetable.ID)));
                subject = cursor.getString(cursor.getColumnIndex(DBContract.Timetable.SUBJECT));
                day = cursor.getString(cursor.getColumnIndex(DBContract.Timetable.DAY));
                start = Long.parseLong(cursor.getString(cursor.getColumnIndex(DBContract.Timetable.ENDTIME)));
                end = Long.parseLong(cursor.getString(cursor.getColumnIndex(DBContract.Timetable.ENDTIME)));

                SetterTimetable setter = new SetterTimetable(id, subject, day, start, end);

                publishProgress(setter);
            }

            return count;
        }

        @Override
        protected void onProgressUpdate(SetterTimetable... values) {
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
            int layoutIdForListItem = R.layout.row_timetable;
            LayoutInflater inflater = LayoutInflater.from(context);
            boolean shouldAttachToParentImmediately = false;

            View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
            Holder holder = new Holder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.bind((SetterTimetable) listAdapter.get(position));
        }

        @Override
        public int getItemCount() {
            return listAdapter.size();
        }

        class Holder extends RecyclerView.ViewHolder {
            TextView tvSubject, tvTime;
            ImageView ivDelete;

            Holder(View itemView) {
                super(itemView);
                tvSubject = itemView.findViewById(R.id.tvSubject);
                tvTime = itemView.findViewById(R.id.tvTime);
                ivDelete = itemView.findViewById(R.id.ivDelete);
            }

            void bind(final SetterTimetable setter) {
                String time = xConversions.getTime(setter.getStartTime()) + " - " + xConversions.getTime(setter.getEndTime());
                tvSubject.setText(setter.getSubject());
                tvTime.setText(time);
                ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (xConversions.deleteRecord(db, DBContract.Timetable.TABLE_NAME, DBContract.Timetable.ID,
                                String.valueOf(setter.getId())))
                            new BackTask().execute();
                    }
                });
            }
        }

    }

}
