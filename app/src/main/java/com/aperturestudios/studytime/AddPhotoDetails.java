package com.aperturestudios.studytime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AddPhotoDetails extends AppCompatActivity {

    Context context;
    XMethods xMethods;
    ArrayAdapter<String> spinnerAdapter;
    String[] arraySpinner = new String[]{};
    DBOperations dbOperations;
    SQLiteDatabase db;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    ImageView ivBitmap;
    CardView cardButton;
    Spinner spinner;
    EditText etTopic, etChapter;
    String photoPath;
    String chosenSubject = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        xMethods = new XMethods(context);
        dbOperations = new DBOperations(context);
        db = dbOperations.getWritableDatabase();
        prefs = getSharedPreferences(AppInfo.USER_INFO, Context.MODE_PRIVATE);

        photoPath = getIntent().getStringExtra("photoPath");
        Bitmap bitmap = xMethods.getBitmapFromPath(photoPath);

        ivBitmap = findViewById(R.id.ivBitmap);
        cardButton = findViewById(R.id.cardButton);
        spinner = findViewById(R.id.spinner);
        etTopic = findViewById(R.id.etTopic);
        etChapter = findViewById(R.id.etChapter);

        ivBitmap.setImageBitmap(bitmap);

        arraySpinner = xMethods.getArrayFromList(dbOperations.getSubjectNames(db));

        spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, arraySpinner);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    chosenSubject = String.valueOf(parent.getItemAtPosition(position));
                else
                    Toast.makeText(context, "Select subject or enter subjects in app menu", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void saveImage(View view) {
        String chapter = etChapter.getText().toString().trim();
        String topic = etTopic.getText().toString().trim();
        String phoneNumber = prefs.getString(AppInfo.PHONE_NUMBER, "0101");
        String studentName = prefs.getString(AppInfo.STUDENT_NAME, "");

        if (chapter.isEmpty())
            Toast.makeText(context, "The chapter is missing", Toast.LENGTH_SHORT).show();
        else if (topic.isEmpty())
            Toast.makeText(context, "The topic is missing", Toast.LENGTH_SHORT).show();
        else if (chosenSubject.equals(""))
            Toast.makeText(context, "Select the subject", Toast.LENGTH_SHORT).show();
        else {
            //Save to db.
            //key, phoneNumber, studentName, localUrl, onlineUrl, subject, chapter, topic;
            //    private long timestamp;
            String key = FirebaseDatabase.getInstance().getReference().child("Notes").child(phoneNumber).push().getKey();
            SetterNotes setterNotes = new SetterNotes(key, phoneNumber, studentName, photoPath, "",
                    chosenSubject, chapter, topic, System.currentTimeMillis());

            new InsertNotes(context).execute(setterNotes);
            startActivity(new Intent(context, MainActivity.class));
        }
    }
}
