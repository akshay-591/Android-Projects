package com.kumar.akshay.tasktimer;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

/**
 * This class is used to retrieve data from database using Loader manager and Cursor Loader
 * which retrieve the data in background thread
 */
class LoaderDatabaseQuery implements LoaderManager.LoaderCallbacks<Cursor>  {
    private static final String TAG = "LoaderDatabaseQuery";

    /**
     * whichever class calling this class should implements this interface
     */
    interface OnQueryDownload {
        void OnQueryAvailabel(@NonNull Loader<Cursor> loader, Cursor data);
        void queryLoaderReset(@NonNull Loader<Cursor> loader);
    }

    private final int LOADER_ID=0;
    private OnQueryDownload onQueryDownload;
    private Context context;




    /**
     *
     * @param onQueryListener Activity/Fragment object reference of that class which is implementing the methods of this onQueryListener interface methods.
     * @param context Context of the Activity/fragment which is calling this class.
     * @param loaderManager basically LoaderManager.getInstance(this).
     */

    public LoaderDatabaseQuery(OnQueryDownload onQueryListener, Context context, LoaderManager loaderManager) {
        this.onQueryDownload = onQueryListener;
        this.context = context;
        Loader<Cursor> cursorLoader = loaderManager.initLoader(LOADER_ID, null,  this);

    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(TAG, "onCreateLoader: Starts with ID  "+id);

        String[] projection = {TaskContract.Columns._id,
                TaskContract.Columns.TASK_NAME,
                TaskContract.Columns.TASKS_DESCRIPTION,
                TaskContract.Columns.TASKS_SORTORDER
        };

        String sortOrder = TaskContract.Columns.TASKS_SORTORDER;

        switch (id){
            case LOADER_ID:
                // creating new Loader(Cursor Loader)
                return new CursorLoader(context,TaskContract.CONTENT_URI,projection,null,null,TaskContract.Columns._id);
            default:
                throw new IllegalThreadStateException("id = "+id);
        }
    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished: starts");
        onQueryDownload.OnQueryAvailabel(loader,data);
        Log.d(TAG, "onLoadFinished: ends");
    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset: starts");
        onQueryDownload.queryLoaderReset(loader);
    }
}

