package com.kumar.akshay.tasktimer;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import javax.xml.datatype.Duration;

/**
 *this is Content provider for the task timer app.
 * only class knows about (@Link AppDatabase)
 * this will be accessed by the Content Resolver
 * this class can be used for providing data from the table
 * Inserting, Updating and Deleting the Content in Table.
 *
 */

public class AppProvider extends ContentProvider {
    private static final String TAG = "AppProvider";
    private AppDatabase openHelper;

    //name of the provider
    static final String CONTENT_AUTHORITY ="com.kumar.akshay.tasktimer.provider";
    //create URI
    public static final Uri CONTENT_AUTHORITY_URI =Uri.parse("content://"+CONTENT_AUTHORITY);

    private static final int TASK=100;
    private static final int TASK_ID=101;

    private static final int TIMING = 200;
    private static final int TIMING_ID=201;

    /*private static final int TASK_TIMING=300;
    private static final int TASK_TIMING_ID=301;
    *
    * */

    private static final int TASK_DURATION=400;
    private static final int TASKS_DURATION_ID=401;


    private static final UriMatcher uriMatcher=builderUriMatcher();

    private static UriMatcher builderUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        //eg- Content://com.kumar.akshay.tasktimer.provider/Tasks
        matcher.addURI(CONTENT_AUTHORITY,TaskContract.TABLE_NAME,TASK);
        //eg- Content://com.kumar.akshay.tasktimer.provider/Tasks/8
        matcher.addURI(CONTENT_AUTHORITY,TaskContract.TABLE_NAME+"/#",TASK_ID);

       /* matcher.addURI(CONTENT_AUTHORITY,TimingContract.TABLE_NAME,TIMING);
        matcher.addURI(CONTENT_AUTHORITY,TimingContract.TABLE_NAME+"/#",TIMING_ID);

        matcher.addURI(CONTENT_AUTHORITY,DurationContract.TABLE_NAME,TASK_DURATION);
        matcher.addURI(CONTENT_AUTHORITY,DurationContract.TABLE_NAME+"/#",TASKS_DURATION_ID);*/

        return matcher;

    }
    @Override
    public boolean onCreate() {
        openHelper = AppDatabase.getInstance(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
       
        Log.d(TAG, "query: called with URI = "+uri.toString());

        UriMatcher addedUri = uriMatcher; //calling builderUriMatcher()
        final int match = addedUri.match(uri); //matching added URIs with given URI

        //Creating object Reference of SQLiteQUeryBuilder class which will be used to make and store query
       // SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor;
        Log.d(TAG, "query: match value ="+match);
        switch (match) {
            case TASK:  // match value is 100

                //Build Query (ex-SELECT projections FROM TABLE Tasks;)
                //queryBuilder.setTables(TaskContract.TABLE_NAME);
                cursor= db.query(TaskContract.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;

            case TASK_ID:  // match Value is 101

                //Build query (SELECT projection FROM TABLE TASKS)
               // queryBuilder.setTables(TaskContract.TABLE_NAME);
                //call getTaskId() method and pass the URI
                long taskId = TaskContract.getTaskId(uri);
                String selectioncriteria = TaskContract.Columns._id+" = "+taskId;
                //add WHERE clause with return Id in existing Query (SELECT "projection" FROM TABLE Tasks WHERE  Tasks._id=#;)
                //queryBuilder.appendWhere(TaskContract.Columns._id+" = "+taskId);
                cursor = db.query(TaskContract.TABLE_NAME,projection,selectioncriteria,selectionArgs,null,null,sortOrder);
                break;

          /*  case TIMING:
                queryBuilder.setTables(TimingContract.TABLE_NAME);
                break;

            case TIMING_ID:
                queryBuilder.setTables(TimingContract.TABLE_NAME);
                long timingId =TimingContract.getTaskId(uri);
                queryBuilder.appendWhere(TimingContract.Columns._id+" = "+timingId);
                break;

            case TASK_DURATION:
                queryBuilder.setTables(DurationContract.TABLE_NAME);
                break;

            case TASKS_DURATION_ID:
                queryBuilder.setTables(DurationContract.TABLE_NAME);
                long durationId = Task_Duration_Contract.getTaskId(uri);
                queryBuilder.appendWhere(DurationContract.Columns._id+" = "+durationId);
                break;*/

            default:
                throw new IllegalArgumentException("Unkonwn URI "+uri);
        }
        //calling getReadableDatabase() method which call The onCreate Method which will either open the Database or Create it
        //SQLiteDatabase db = openHelper.getReadableDatabase();
        //calling query method of SQLiteQueryBuilder class which will perform given query in database with other following clause if given.

        //Cursor cursor = queryBuilder.query(db,projection,selection,selectionArgs,null,null,sortOrder);

        /**
         * this methods will register this query method cursor for receiving notification if any changes made to the passed uri.
         */
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        Log.d(TAG, "query: exiting ");
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "inserting Values: uri = "+uri.toString());
        Log.d(TAG, "insert: Value = "+values.valueSet());
        final int match =uriMatcher.match(uri);

        final SQLiteDatabase db;
        long recodId;
        final Uri returnUri;
        Log.d(TAG, "insert: match value = "+match);
        switch (match){
            case TASK:
                db=openHelper.getWritableDatabase();
                recodId=db.insert(TaskContract.TABLE_NAME,null,values);
                Log.d(TAG, "insert: record Id = "+recodId);
                if(recodId>=0){
                    returnUri= TaskContract.buildTaskUri(recodId);
                } else{
                    throw new android.database.SQLException("failed to insert "+uri.toString());
                }
                break;

            /*case TIMING:
                db=openHelper.getWritableDatabase();
                recodId=db.insert(TimingContract.TABLE_NAME,null,values);
                if(recodId>=0){
                    returnUri =TimingContract.buildTimingUri(recodId);
                } else {
                    throw new android.database.SQLException("fail to insert row " +uri.toString());
                }
                break;*/

            default:
                throw new IllegalStateException("unkown Uri "+uri.toString());
        }
        if(recodId>=0){
            Log.d(TAG, "insert: notify resolver "+uri.toString());
            /**
             * this method will notify all the regisred listeners who are watching this passed uri for changes
             * and also notify to the observer which are passed to thi method.
             */
            getContext().getContentResolver().notifyChange(uri,null);
        }else{
            Log.d(TAG, "insert: nothing inserted");
        }

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "delete content "+uri.toString());
        final int match =uriMatcher.match(uri);
        final SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch (match){
            case TASK:
                db=openHelper.getWritableDatabase();
                count=db.delete(TaskContract.TABLE_NAME,selection,selectionArgs);
                break;

            case TASK_ID:
                db=openHelper.getWritableDatabase();
               long taskId=TaskContract.getTaskId(uri);
                selectionCriteria= TaskContract.Columns._id+" = "+taskId;
                count =db.delete(TaskContract.TABLE_NAME,selectionCriteria,selectionArgs);
                break;

            /*case TIMING:
                db=openHelper.getWritableDatabase();
                count=db.delete(TimingContract.TABLE_NAME,selection,selectionArgs);
                break;

            case TIMING_ID:
                db=openHelper.getWritableDatabase();
                long timingId=TimingContract.getTaskId(uri);
                selectionCriteria= TimingContract.Columns._id+" = "+timingId;
                count =db.delete(TimingContract.TABLE_NAME,selectionCriteria,selectionArgs);
                break;*/
            default:
                throw new IllegalStateException("Unknown URI "+uri.toString());

        }

        if (count > 0) {
            Log.d(TAG, "delete: notify resolver"+uri);
            getContext().getContentResolver().notifyChange(uri,null);
        }else{
            Log.d(TAG, "delete: nothing deleted");
        }

        Log.d(TAG, "delete Complete "+count);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, " starting update: uri = "+uri.toString());
        final int match = uriMatcher.match(uri);
        Log.d(TAG, "update: match no. = "+match);

        final SQLiteDatabase db;
        int count;
        String selectionCriteria;

        switch (match){
            case TASK:
                db=openHelper.getWritableDatabase();
                count=db.update(TaskContract.TABLE_NAME,values,selection,selectionArgs);
                break;
            case TASK_ID:
                db=openHelper.getWritableDatabase();
                long taskId = TaskContract.getTaskId(uri);
                selectionCriteria=TaskContract.Columns._id+" = "+taskId;
                count=db.update(TaskContract.TABLE_NAME,values,selectionCriteria,selectionArgs);
                break;
          /* case TIMING:
                db=openHelper.getWritableDatabase();
                count=db.update(TimingContract.TABLE_NAME,values,selection,selectionArgs);
                break;
            case TIMING_ID:
                db=openHelper.getWritableDatabase();
                long timingId = TimingContract.getTimingId(uri);
                selectionCriteria=TimingContract.Columns._id+" = "+timingId;
                count=db.update(TimingContract.TABLE_NAME,values,selectionCriteria,selectionArgs);
                break;*/
            default:
                throw new IllegalStateException("unknown uri  = "+uri.toString());

        }

        if (count > 0) {
            Log.d(TAG, "update: notify resolver"+uri);
            getContext().getContentResolver().notifyChange(uri,null);
        }else{
            Log.d(TAG, "update: nothing updated");
        }


        Log.d(TAG, "Exiting update returning = "+count);
        return count;
    }
}
