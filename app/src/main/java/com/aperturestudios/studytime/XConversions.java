package com.aperturestudios.studytime;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.transition.TransitionManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

class XConversions {
    private Context context;

    XConversions(Context context) {
        this.context = context;
    }





    boolean deleteRecord(SQLiteDatabase db, String tableName, String record, String value) {
        return db.delete(tableName, record + "=?", new String[]{value}) > 0;
    }

    List getListFromStringArray(List list, String[] array) {
        for (String anArray : array) {
            list.add(anArray);
        }

        return list;
    }

    String[] getArrayFromList(List list, SetterSubjects setter) {
        String[] arrayMax = new String[]{"Choose category"};
        List<String> listKeys = new LinkedList<>(Arrays.asList(arrayMax));
        for (int x = 0; x < list.size(); x++) {
            SetterSubjects setterCategories = (SetterSubjects) list.get(x);
            listKeys.add(setterCategories.getName());
        }

        String[] stockArr = new String[listKeys.size()];
        stockArr = listKeys.toArray(stockArr);

        return stockArr;
    }

    void insertGlideImage(String imageLink, ImageView ivImage) {
        try {
            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageLink);
            Glide.with(context)
                    .using(new FirebaseImageLoader())
                    .load(storageReference)/*
                    .placeholder(circularProgressDrawable())*/
                    .into(ivImage);
        } catch (Exception e) {
            //
        }
    }

    void initializeRecyclerviewLayouts(RecyclerView[] recyclerViews, int layout) {
        for (int x = 0; x < recyclerViews.length; x++) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, layout, false);
            recyclerViews[x].setLayoutManager(layoutManager);
            recyclerViews[x].setHasFixedSize(true);
        }
    }

    LinearLayoutManager initializeSingleRecyclerviewLayouts(RecyclerView recyclerViews, int layout) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, layout, false);
            recyclerViews.setLayoutManager(layoutManager);
            recyclerViews.setHasFixedSize(true);

            return layoutManager;
    }

    void spinnerSetAdapter(Spinner spinner, String[] array) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, array);
        spinner.setAdapter(adapter);
    }

    public void setSpinnerAdapter(Spinner spinner, List listCategories) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_dropdown_item, getArrayFromList(listCategories, new SetterSubjects()));
        spinner.setAdapter(adapter);
    }

    CircularProgressDrawable circularProgressDrawable() {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();

        return circularProgressDrawable;
    }

    void setPriceAndDiscount(double discount, double price, TextView tvPrice, TextView tvDiscount) {
        if (discount == 0) {
            tvDiscount.setText("");
            tvPrice.setText(getFullPrice(price));
        } else {
            double newPrice = price - ((discount/100) * price);
            String dis = "was " + getFullPrice(price) + " now its ";
            tvDiscount.setText(dis);
            tvPrice.setText(getFullPrice(newPrice));
        }
    }

    void addAnotherZero(String amount) {
        if (amount.length() - amount.indexOf(".") - 1 < 2)
            amount += "0";
    }




    String getFullPrice(double price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String numberAsString = decimalFormat.format(price);

        if (numberAsString.length() - (numberAsString.indexOf(".") + 1) < 2)
            numberAsString += "0";

        return "$" + numberAsString;
    }

    double getPrice(double price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String numberAsString = decimalFormat.format(price);

        return Double.parseDouble(numberAsString);
    }

    String getFormattedDouble(double price) {
        String charge = String.valueOf(price);
        if (charge.length() - (charge.indexOf(".") + 1) < 2)
            charge += "0";

        return charge;
    }




    public boolean isWithinDeliveryTime(int startHour, int endHour, int minute) {
        long currentTime = System.currentTimeMillis();
        if (startHour != 0 && endHour != 0) {
            long deliveryTimeEnd = getHourTimestamp(endHour, minute);
            return deliveryTimeEnd - currentTime > (60000 * 40);
        } else
            return true;
    }

    private long getHourTimestamp(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        return calendar.getTimeInMillis();
    }

    public String getOrderNumber() {
        String[] alpha = {"", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        Random random = new Random();
        String a = alpha[checkNum(random.nextInt(26))];
        String b = alpha[checkNum(random.nextInt(26))];
        int c = random.nextInt(9);
        int d = random.nextInt(9);
        int e = random.nextInt(9);
        int f = random.nextInt(9);
        String g = alpha[checkNum(random.nextInt(26))];
        String h = alpha[checkNum(random.nextInt(26))];

        //return a + b + g + h + "-" + getDate(false) + "-" + getDate(true);
        return a + c + d + e + f;
    }

    public void setTextColor(TextView textView, int textColor, boolean isBold) {
        textView.setTextColor(context.getResources().getColor(textColor));
        if (isBold) {
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextSize(17);
        } else {
            textView.setTypeface(null, Typeface.NORMAL);
            textView.setTextSize(13);
        }
    }

    void showToast(String message, boolean isLong) {
        if (isLong)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }







    private int checkNum(int a) {
        if (a == 0 || a == 27) {
            if (a == 0) {
                a += 1;
            }
            if (a == 27) {
                a -= 1;
            }
        }

        return a;
    }

    public String getDate(long timestamp) {
        String[] dates = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);

        String day = getTheValue(calendar.get(Calendar.DAY_OF_MONTH));
        int month = calendar.get(Calendar.MONTH) + 1;
        String year = String.valueOf(calendar.get(Calendar.YEAR)).substring(2, 4);
        String hour = getTheValue(calendar.get(Calendar.HOUR_OF_DAY));
        String min = getTheValue(calendar.get(Calendar.MINUTE));

        return day + "-" + dates[month] + "-" + year;
    }

    private String getTheValue(int value){
        String theValue = String.valueOf(value);
        if (theValue.length() == 1){
            return "0"+theValue;
        } else {
            return theValue;
        }
    }

    String getFullDate(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);

        String day = getTheValue(calendar.get(Calendar.DAY_OF_MONTH));
        String month = getTheValue(calendar.get(Calendar.MONTH) + 1);
        String year = String.valueOf(calendar.get(Calendar.YEAR)).substring(2, 4);
        String hour = getTheValue(calendar.get(Calendar.HOUR_OF_DAY));
        String min = getTheValue(calendar.get(Calendar.MINUTE));

        return hour + ":" + min + " " + day + "-" + month + "-" + year;
    }

    String getWeekDay(long timestamp) {
        String[] weekDays = {"", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);

        int day = calendar.get(Calendar.DAY_OF_WEEK);

        return weekDays[day];
    }

    String getTime(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);

        String day = getTheValue(calendar.get(Calendar.DAY_OF_MONTH));
        String month = getTheValue(calendar.get(Calendar.MONTH) + 1);
        String year = String.valueOf(calendar.get(Calendar.YEAR)).substring(2, 4);
        String hour = getTheValue(calendar.get(Calendar.HOUR_OF_DAY));
        String min = getTheValue(calendar.get(Calendar.MINUTE));

        return hour + ":" + min;
    }

    String getTimeLeft(long deliverBefore) {
        long currentTimestamp = System.currentTimeMillis();
        if (currentTimestamp > deliverBefore)
            return "Due";

        long sec = 1000;
        long min = 60000;
        long hour = 3600000;
        long day = 86400000;

        long timeBetween = deliverBefore - currentTimestamp;
        long days = timeBetween / day;
        long daysMod = timeBetween % day;
        long hours = daysMod / hour;
        long hoursMod = daysMod % hour;
        long minutes = hoursMod / min;

        StringBuilder builder = new StringBuilder();
        builder.append(getTheValue(hours)).append(" hrs ").append(getTheValue(minutes)).append(" min");

        return String.valueOf(builder);
    }

    private String getTheValue(long value){
        String theValue = String.valueOf(value);
        if (theValue.length() == 1){
            return "0"+theValue;
        } else {
            return theValue;
        }
    }

    public void openEcocashDialer(String code) {
        String harsh = Uri.encode("#");
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:*151*2*2*" + code + harsh));
        context.startActivity(intent);
    }


    public void swipeViews(ConstraintLayout constLayout, ConstraintSet constraintSet) {
        TransitionManager.beginDelayedTransition(constLayout);
        constraintSet.applyTo(constLayout);
    }
}
