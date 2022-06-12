package com.aperturestudios.studytime;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

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

    CameraPhoto cameraPhoto;
    final int CAMERA_REQUEST = 123;
    GalleryPhoto galleryPhoto;
    private static final int GALLERY_REQUEST = 27277;
    private static final int REQUEST_WRITE_IMAGE = 1994;
    private static String LINK = "";

    ImageView ivImage;
    ProgressBar progressBar;
    TextView tvEmpty;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    String imageFilePath;
    private static final int REQUEST_CAPTURE_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        xConversions = new XConversions(context);
        xMethods = new XMethods(context);
        dbOperations = new DBOperations(context);
        db = dbOperations.getWritableDatabase();
        listKeys = dbOperations.getNoteKeys(db);
        list = new ArrayList();

        FirebaseAuth.getInstance().signInAnonymously();
        prefs = getSharedPreferences(AppInfo.USER_INFO, Context.MODE_PRIVATE);
        galleryPhoto = new GalleryPhoto(context);
        cameraPhoto = new CameraPhoto(context);
        fabCamera = findViewById(R.id.fabCamera);
        tvEmpty = findViewById(R.id.tvEmpty);
        recyclerView = findViewById(R.id.recyclerView);
        //startActivity(new Intent(context, NoteView.class));

        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageFromCamera();
                //startActivity(new Intent(context, AddPhotoDetails.class));
            }
        });
        recyclerView.setLayoutManager(
            xMethods.initializeSingleRecyclerviewLayouts(recyclerView, LinearLayoutManager.VERTICAL)
        );

        new BackTask().execute();
    }

    private void insertBroadcastImage() {
        /*if (!prefs.getString(AppInfo.FORM_LINK, "").equals("")) {
            LINK = prefs.getString(AppInfo.FORM_LINK, "");*/

            try {
                StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(LINK);
                Glide.with(this)
                        .using(new FirebaseImageLoader())
                        .load(storageReference)
                        .crossFade()
                        .into(ivImage);
            } catch (Exception e) {
                //
            }
        //}
    }

    public void openSearch(View view) {
        startActivity(new Intent(context, Search.class));
    }

    public void openSettings(View view) {
        startActivity(new Intent(context, Settings.class));
    }

    private class BackTask extends AsyncTask<Void, SetterNotes, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            Cursor cursor = dbOperations.getNotes(db);
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
            int layoutIdForListItem = R.layout.row_notes;
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
            TextView tvName, tvSubject, tvChapter, tvTopic, tvTime, tvDate;
            ImageView ivProfile, ivBitmap;

            Holder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvName);
                ivBitmap = itemView.findViewById(R.id.ivBitmap);
                tvSubject = itemView.findViewById(R.id.tvSubject);
                tvChapter = itemView.findViewById(R.id.tvChapter);
                tvTopic = itemView.findViewById(R.id.tvTopic);
                tvTime = itemView.findViewById(R.id.tvTime);
                tvDate = itemView.findViewById(R.id.tvDate);
            }

            void bind(final SetterNotes setter) {
                try {
                    StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(setter.getOnlineUrl());
                    Glide.with(context)
                            .using(new FirebaseImageLoader())
                            .load(storageReference)
                            .crossFade()
                            .into(ivBitmap);
                } catch (Exception e) {
                    Glide.with(context)
                            .load(setter.getLocalUrl())
                            .crossFade()
                            .into(ivBitmap);
                }

                tvName.setText(setter.getStudentName().trim().equals("") ? setter.getStudentName() : "Student name");
                tvSubject.setText(setter.getSubject());
                tvChapter.setText(setter.getChapter());
                tvTopic.setText(setter.getTopic());
                tvTime.setText(xConversions.getTime(setter.getTimestamp()));
                tvDate.setText(xConversions.getDate(setter.getTimestamp()));
            }

        }

    }







    public void getImageFromStorage(View view) {
        openGalleryIntent();
    }

    private void getImageFromCamera() {
        if (Build.VERSION.SDK_INT >= 23) {
            boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
            if (!hasPermission) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_REQUEST);
            } else {
                launchCamera();
            }
        } else {
            launchCamera();
        }
    }

    private void launchCamera() {
        try {
            startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQUEST);
            //cameraPhoto.addToGallery();
        } catch (IOException e) {
            Toast.makeText(context, "Something went wrong while taking a photo\n\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void openGalleryIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(getPackageManager()) != null){

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(context, ""  + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,context.getPackageName() + ".provider", photoFile);

                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);
            }
        } else
            getImageFromStorage();
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    private void getImageFromStorage() {
        if (Build.VERSION.SDK_INT >= 23) {
            boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
            if (!hasPermission) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_WRITE_IMAGE);
            } else {
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
            }
        } else {
            startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            handlePosterResult(data);
        }
        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
            String photoPath = cameraPhoto.getPhotoPath();
            handlePosterResult(photoPath);
        }

        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            handlePosterResult(imageFilePath);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_IMAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //reload my activity with permission granted or use the features that required the permission
                    startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
                } else {
                    Toast.makeText(context, "Allow access to your storage.", Toast.LENGTH_LONG).show();
                }
                break;
            case CAMERA_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //reload my activity with permission granted or use the features that required the permission
                    launchCamera();
                } else {
                    Toast.makeText(context, "Allow access to your camera.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void handlePosterResult(Intent data) {
        Uri uri = data.getData();
        //progressBar.setVisibility(View.VISIBLE);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        final StorageReference imageRef = storageRef.child("meal-pics/"+ uri.getLastPathSegment());
        UploadTask uploadTask = imageRef.putFile(uri);
        //Toast.makeText(context, "Uri: " + uri.toString(), Toast.LENGTH_LONG).show();

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(context, "Image UploadFailed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-accType, and download URL.
                Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        LINK = uri.toString();

                        /*editor = prefs.edit();
                        editor.putString(AppInfo.FORM_LINK, LINK);
                        editor.apply();*/

                        try {
                            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(uri.toString());
                            Glide.with(context)
                                    .using(new FirebaseImageLoader())
                                    .load(storageReference)
                                    .crossFade()
                                    .into(ivImage);
                            progressBar.setVisibility(View.GONE);
                        } catch (Exception e) {
                            //
                        }
                    }
                });
            }
        });

    }

    private void handlePosterResult(String photoPath) {
        Intent intent = new Intent(context, AddPhotoDetails.class);
        intent.putExtra("photoPath", photoPath);
        startActivity(intent);
    }
}
