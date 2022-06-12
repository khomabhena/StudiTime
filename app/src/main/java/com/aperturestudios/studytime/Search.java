package com.aperturestudios.studytime;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    Context context;
    XConversions xConversions;
    XMethods xMethods;
    DBOperations dbOperations;
    SQLiteDatabase db;
    static List list;
    List listKeys;
    Adapter adapter;

    FloatingActionButton fabCamera;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        xConversions = new XConversions(context);
        xMethods = new XMethods(context);
        dbOperations = new DBOperations(context);
        db = dbOperations.getWritableDatabase();
        listKeys = dbOperations.getNoteKeys(db);
        list = new ArrayList();
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(
                xMethods.initializeSingleRecyclerviewLayouts(recyclerView, LinearLayoutManager.VERTICAL)
        );

        new BackTask().execute();
    }

    private class BackTask extends AsyncTask<Void, SetterNotes, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            Cursor cursor = dbOperations.getSearchDefault(db);
            int count = cursor.getCount();

            String key, phoneNumber, studentName, localUrl, onlineUrl, subject, chapter, topic;
            long timestamp;

            list = new ArrayList();
            while (cursor.moveToNext()) {

                key = cursor.getString(cursor.getColumnIndex(DBContract.Notes.KEY));
                phoneNumber = cursor.getString(cursor.getColumnIndex(DBContract.Notes.PHONE_NUMBER));
                studentName = cursor.getString(cursor.getColumnIndex(DBContract.Notes.STUDENT_NAME));
                localUrl = cursor.getString(cursor.getColumnIndex(DBContract.Notes.LOCAL_URL));
                onlineUrl = cursor.getString(cursor.getColumnIndex(DBContract.Notes.ONLINE_URL));
                subject = cursor.getString(cursor.getColumnIndex(DBContract.Notes.SUBJECT));
                chapter = cursor.getString(cursor.getColumnIndex(DBContract.Notes.CHAPTER));
                topic = cursor.getString(cursor.getColumnIndex(DBContract.Notes.TOPIC));
                timestamp = Long.parseLong(cursor.getString(cursor.getColumnIndex(DBContract.Notes.TIMESTAMP)));

                SetterNotes setter = new SetterNotes(key, phoneNumber, studentName,
                        localUrl, onlineUrl, subject, chapter, topic, timestamp);

                publishProgress(setter);
            }

            return count;
        }

        @Override
        protected void onProgressUpdate(SetterNotes... values) {
            list.add(values[0]);
        }

        @Override
        protected void onPostExecute(Integer count) {
            Toast.makeText(context, "Counting: " + count, Toast.LENGTH_SHORT).show();
            if (count != 0) {
                adapter = new Adapter(list, context);
                recyclerView.setAdapter(adapter);
            }
        }
    }

    class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

        private List listAdapter;
        Context context;
        List<String> listTime;

        public Adapter(List listAdapter, Context context) {
            this.listAdapter = listAdapter;
            this.context = context;
            listTime = new ArrayList<>();
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            int layoutIdForListItem = R.layout.row_search_default;
            LayoutInflater inflater = LayoutInflater.from(context);
            boolean shouldAttachToParentImmediately = false;

            View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
            Holder holder = new Holder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.bind((SetterNotes) listAdapter.get(position));
        }

        @Override
        public int getItemCount() {
            return listAdapter.size();
        }

        class Holder extends RecyclerView.ViewHolder {
            TextView tvChapter, tvSubject, tvNotes, tvTopic;

            Holder(View itemView) {
                super(itemView);
                tvSubject = itemView.findViewById(R.id.tvSubject);
                tvChapter = itemView.findViewById(R.id.tvChapter);
                tvNotes = itemView.findViewById(R.id.tvNotes);
                tvTopic = itemView.findViewById(R.id.tvTopics);
            }

            void bind(final SetterNotes setter) {
                tvSubject.setText(setter.getSubject());
                tvChapter.setText(setter.getChapter());
            }

        }

    }

}
