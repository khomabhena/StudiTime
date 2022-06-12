package com.aperturestudios.studytime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import uk.co.senab.photoview.PhotoViewAttacher;

public class NoteView extends AppCompatActivity {

    Context context;
    ImageView ivBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        ivBitmap = findViewById(R.id.ivBitmap);
        PhotoViewAttacher photoView = new PhotoViewAttacher(ivBitmap);
        photoView.update();

        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("*/*");
        startActivityForResult(i, 2012);
    }

    /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
      intent.setType("file/*");
      startActivityForResult(intent, PICKFILE_REQUEST_CODE);*/

}
