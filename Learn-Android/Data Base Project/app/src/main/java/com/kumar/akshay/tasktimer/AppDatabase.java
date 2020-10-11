package com.kumar.akshay.tasktimer;
/**
 *
 *Basic class in this application for creating/ opening database
 * and making any changes to it ex- adding tables etc.
 *The only class use this call (@Link AppProvider).
 *
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class AppDatabase extends SQLiteOpenHelper {
    private static final String TAG = "AppDatabase";
    public static final String TASK_DATABASE_NAME = "TaskTimer.db";
    public static final int DATABASE_VERSION =1;
    private static AppDatabase appDatabase=null;

    private AppDatabase(Context context){
        super(context,TASK_DATABASE_NAME,null,DATABASE_VERSION);
    }

    /**
     * this is where tables in the datbase created first time.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sSQL; //SQL statement
        Log.d(TAG, "SQL onCreate: start");
        //creating SQL query to create table Tasks in the database
        sSQL="CREATE TABLE "
                +TaskContract.TABLE_NAME+" ("
                +TaskContract.Columns._id+" INTEGER PRIMARY KEY NOT NULL,"
                +TaskContract.Columns.TASK_NAME+" TEXT,"
                +TaskContract.Columns.TASKS_DESCRIPTION+" TEXT,"
                +TaskContract.Columns.TASKS_SORTORDER+" INTEGER);";
        //executing Query
        db.execSQL(sSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: starts");
        switch (oldVersion){
            case 1:
                //upgrade logic from version
                break;
            default:
                throw new IllegalStateException("onUpgrade new version "+newVersion);
        }


    }

    /**
     * as this class is singletone class that means only one object of this class can be created
     * and that object cannot be created using new operator.
     *this method will create or return the existing object of  this class.
     *
     * @param context
     * @return Appdatabase object
     */

    public static AppDatabase getInstance(Context context){
        Log.d(TAG, "getInstance: starts");
        if(appDatabase==null){
            Log.d(TAG, "getInstance: object created");
            appDatabase=new AppDatabase(context);
        }
        return appDatabase;

    }
}
