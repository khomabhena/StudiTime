package com.aperturestudios.studytime;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AddSubject extends AppCompatActivity {

    Context context;
    XConversions xConversions;
    EditText etSubject;
    RecyclerView recyclerView;

    DBOperations dbOperations;
    SQLiteDatabase db;
    List list;
    List<String> listKeys;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        xConversions = new XConversions(context);
        dbOperations = new DBOperations(context);
        db = dbOperations.getWritableDatabase();
        list = new ArrayList();
        listKeys = dbOperations.getSubjectKeys(db);

        etSubject = findViewById(R.id.etSubject);
        recyclerView = findViewById(R.id.recyclerView);

        xConversions.initializeSingleRecyclerviewLayouts(recyclerView, LinearLayoutManager.VERTICAL);
        new BackTask().execute();
    }

    public void addSubject(View view) {
        String subject = etSubject.getText().toString().trim();

        if (subject.equals(""))
            Toast.makeText(context, "Enter your subject", Toast.LENGTH_LONG).show();
        else {
            SetterSubjects setterSubjects = new SetterSubjects(subject);
            new InsertSubjects(context, listKeys).execute(setterSubjects);
            new BackTask().execute();
            etSubject.setText("");
        }
    }

    int count2 = 0;
    private class BackTask extends AsyncTask<Void, SetterSubjects, Integer> {

        List listInternal;

        @Override
        protected Integer doInBackground(Void... params) {

            Cursor cursor = dbOperations.getSubjects(db);
            int count = cursor.getCount(); count2 = cursor.getCount();

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
                ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (xConversions.deleteRecord(db, DBContract.Subjects.TABLE_NAME, DBContract.Subjects.NAME, setter.getName()))
                            new BackTask().execute();
                    }
                });
            }
        }

    }

}
