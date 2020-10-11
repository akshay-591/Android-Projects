package com.kumar.akshay.v_player;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kumar.akshay.v_player.databinding.ActivityVideoGalleryBinding;

public class Video_Gallery extends AppCompatActivity implements VideoGalleryRecyclerAdapter.OnVideoListener,
        CustomLoader.onQueryUpdated {

    private static final String TAG = "Video_Gallery";
    public static final int GALLERY_LOADER_ID = 2;
    private VideoGalleryRecyclerAdapter videoGalleryRecyclerAdapter;

    private ActivityVideoGalleryBinding galleryBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        galleryBinding = ActivityVideoGalleryBinding.inflate(getLayoutInflater());
        View view = galleryBinding.getRoot();
        setContentView(view);
        setSupportActionBar(galleryBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String bucketId = (String) getIntent().getSerializableExtra(BucketData.class.getSimpleName());


        galleryBinding.galleryView.setLayoutManager(new GridLayoutManager(this, 2));

        videoGalleryRecyclerAdapter = new VideoGalleryRecyclerAdapter(this, null, this);
        galleryBinding.galleryView.setAdapter(videoGalleryRecyclerAdapter);

        new CustomLoader(this, this, LoaderManager.getInstance(this), GALLERY_LOADER_ID, bucketId);


    }

    @Override
    public void onQueryAvailable(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onQueryAvailable: called Loader  ID = " + loader.getId() + "Cursor data count = " + data.getCount());

        switch (loader.getId()) {

            case GALLERY_LOADER_ID:
                Log.d(TAG, "onQueryAvailable: in switch ");
                videoGalleryRecyclerAdapter.swapCursor(data);
                break;

            default:
                //do nothing
        }
    }

    @Override
    public void onQueryReset(@NonNull Loader<Cursor> loader) {
        Log.d(TAG, "onQueryReset: called");
        switch (loader.getId()) {
            case GALLERY_LOADER_ID:
                videoGalleryRecyclerAdapter.swapCursor(null);
                break;

            default:
                //do nothing
        }

    }

    @Override
    public void onVideoClickListener(Uri uri) {
        Log.d(TAG, "onVideoClickListener: called");
        if (uri != null) {
            Intent intent = new Intent(this, VideoPlayerActivity.class);
            intent.setData(uri);
            startActivity(intent);
        } else throw new IllegalStateException("empty Video Uri");

    }
}
