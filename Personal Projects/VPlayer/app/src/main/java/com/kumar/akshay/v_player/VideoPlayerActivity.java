package com.kumar.akshay.v_player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;

import com.kumar.akshay.v_player.databinding.ActivityVideoPlayerBinding;

public class VideoPlayerActivity extends AppCompatActivity {
    private static final String TAG = "VideoPlayerActivity";
    private ActivityVideoPlayerBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);

        mBinding= ActivityVideoPlayerBinding.inflate(getLayoutInflater());
        View view =mBinding.getRoot();
        setContentView(view);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Receiving Uri from Intent Object passed by Video_Gallery activity
        Uri uri=getIntent().getData();

        //setting media controller
        MediaController mediaController = new MediaController(this);
        //setAnchor View means attaching media controller to some View in this case to Video View
        mediaController.setAnchorView(mBinding.videoView);
        mBinding.videoView.setMediaController(mediaController);
        //calling setVideo method which will play the video using uri of the File the uri contain Content Uri + file Id
        mBinding.videoView.setVideoURI(uri);
        //starting Video
        mBinding.videoView.start();
    }
}
