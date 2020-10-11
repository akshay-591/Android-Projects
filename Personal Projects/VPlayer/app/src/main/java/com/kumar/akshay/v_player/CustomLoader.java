package com.kumar.akshay.v_player;


import android.content.Context;
import android.database.Cursor;

import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

class CustomLoader implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "CustomLoader";
    private int loaderId;
    private Context context;
    private onQueryUpdated onQueryUpdated;
    private String bucket_ID;


    /**
     * whichever activity calling this class will have to implements its interfaces
     */
    interface onQueryUpdated {
        void onQueryAvailable(@NonNull Loader<Cursor> loader, Cursor data);

        void onQueryReset(@NonNull Loader<Cursor> loader);
    }

    CustomLoader(Context context, onQueryUpdated queryUpdated, LoaderManager loaderManager, int loaderId) {
        this.context = context;
        this.loaderId = loaderId;
        this.onQueryUpdated = queryUpdated;

       loaderManager.initLoader(loaderId, null, this);

    }

    CustomLoader(Context context, onQueryUpdated queryUpdated, LoaderManager loaderManager, int loaderId, String bucket_ID) {
        this.context = context;
        this.loaderId = loaderId;
        this.onQueryUpdated = queryUpdated;
        this.bucket_ID = bucket_ID;

        if (loaderManager.getLoader(loaderId) != null) {
            loaderManager.destroyLoader(loaderId);
        }
        loaderManager.initLoader(loaderId, null, this);
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(TAG, "onCreateLoader: called with ID = " + id);
        CursorLoader cursorLoader;
        String[] projection;
        String sortOrder;

        switch (id) {
            //For Main Activity
            case MainActivity.VERTICAL_LOADER_ID:
                    projection = new String[]{
                            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                            MediaStore.Video.Media.BUCKET_ID,
                            MediaStore.Video.Media.DATE_MODIFIED,
                            MediaStore.Video.Media.TITLE

                    };
                    sortOrder =MediaStore.Video.Media.BUCKET_ID;


                cursorLoader = new CursorLoader(context,
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        null,
                        null,
                        sortOrder );
                break;

                //for Gallery Activity
            case MainActivity.HORIZONTAL_LOADER_ID:
                projection = new String[]{
                        MediaStore.Video.Media.DISPLAY_NAME,
                        MediaStore.Video.Media._ID
                };
                String selection;

                    selection = MediaStore.Video.Media.BUCKET_ID + " =" + bucket_ID;

                cursorLoader = new CursorLoader(context,
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        selection,
                        null,
                        MediaStore.Video.Media.DATE_ADDED);
                break;

            default:
                throw new IllegalStateException("wrong ID ");
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished: called");
        onQueryUpdated.onQueryAvailable(loader, data);
    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        onQueryUpdated.onQueryReset(loader);
    }
}
