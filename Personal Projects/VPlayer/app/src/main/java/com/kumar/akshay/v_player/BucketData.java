package com.kumar.akshay.v_player;

import android.os.Build;
import android.text.style.TtsSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.sql.Time;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

class BucketData {
    private String bucket_Name;
    private String bucket_ID;
    private long date_modified;

    BucketData(String name, String ID,long date_modified){
        this.bucket_Name=name;
        this.bucket_ID=ID;
        this.date_modified=date_modified;
    }

    public String getBucket_Name() {
        return bucket_Name;
    }

    public String getBucket_ID() {
        return bucket_ID;
    }

   // @RequiresApi(api = Build.VERSION_CODES.O)
    public String getDate_modified() {
        long mills = date_modified*1000;

        Date date = new Date(mills);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formatDate = simpleDateFormat.format(date);


        return "Date Modified :"+ formatDate;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}

