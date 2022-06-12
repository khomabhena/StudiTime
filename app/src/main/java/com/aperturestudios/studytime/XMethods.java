package com.aperturestudios.studytime;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class XMethods {

    Context context;
    DBOperations dbOperations;
    SQLiteDatabase db;

    public XMethods(Context context) {
        this.context = context;
        dbOperations = new DBOperations(context);
        db = dbOperations.getWritableDatabase();
    }

    void uploadImageToFirebase(Intent data) {
        Uri uri = data.getData();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        final StorageReference imageRef = storageRef.child("meal-pics/"+ uri.getLastPathSegment());
        UploadTask uploadTask = imageRef.putFile(uri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(context, "Image UploadFailed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                    }
                });
            }
        });

    }

    void uploadImageToFirebase(final long lastId, SetterNotes setterNotes) {
        Uri uri = Uri.fromFile(new File(setterNotes.getLocalUrl()));

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        final StorageReference imageRef = storageRef.child(setterNotes.getPhoneNumber() + "/" + uri.getLastPathSegment());
        UploadTask uploadTask = imageRef.putFile(uri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(context, "Image UploadFailed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DBContract.Notes.ONLINE_URL, uri.toString());
                        updateDatabaseRecord(db, DBContract.Notes.TABLE_NAME, DBContract.Notes.ID, lastId, contentValues);
                    }
                });
            }
        });
    }

    void sendEmail(String toEmail, String subject, String message, Uri uri) {
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {toEmail});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        context.startActivities(new Intent[]{Intent.createChooser(emailIntent, "Sending Email.....")});
    }

    LinearLayoutManager initializeSingleRecyclerviewLayouts(RecyclerView recyclerViews, int layout) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, layout, false);
        recyclerViews.setLayoutManager(layoutManager);
        recyclerViews.setHasFixedSize(true);

        return layoutManager;
    }

    Bitmap getBitmapFromPath(String photoPath) {
        Bitmap bitmap = null;
        try {
            bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    String[] getArrayFromList(List list) {
        String[] arrayMax = new String[]{"Choose Subject"};
        List<String> listKeys = new LinkedList<>(Arrays.asList(arrayMax));
        for (int x = 0; x < list.size(); x++) {
            listKeys.add(String.valueOf(list.get(x)));
        }

        String[] stockArr = new String[listKeys.size()];
        stockArr = listKeys.toArray(stockArr);

        return stockArr;
    }

    boolean deleteDatabaseRecord(SQLiteDatabase db, String tableName, String record, String value) {
        return db.delete(tableName, record + "=?", new String[]{value}) > 0;
    }

    boolean updateDatabaseRecord(SQLiteDatabase db, String tableName, String record, long dbId, ContentValues contentValues) {
        return db.update(tableName, contentValues,record + "=?", new String[]{String.valueOf(dbId)}) > 0;
    }
}
