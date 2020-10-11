package kumar.akshay.youtubeplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.DialogTitle;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class Player extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private static final String TAG = "Player:";
    static  final String GOOGLE_API_KEY = "The personal Key will be here";
    static final String YOUTUBE_VIDEO_ID = "89VmiBz15Jo"; //Taken from Youtube 
    static final String YOUTUBE_PLAYLIST= "TLPQMjYwMzIwMjDwbFh_xTpgGA"; //Taken from Youtube 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflating the layout and attaching it to Constraint Layout variable
        ConstraintLayout layout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.layout_player, null);
        setContentView(layout);
        //adding YouTubePlayer View in Layout
        // creating YouTubePlayerView object reference and
        // passing context (this) so that it can know about the environment its created in
        YouTubePlayerView playerView = new YouTubePlayerView(this);
        //giving dimension to the View
        playerView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //adding View in Layout
        layout.addView((playerView));
        //initializing player by giving API key and context
        playerView.initialize(GOOGLE_API_KEY,this);


    }
     //this method implementations will get executed if initialization will get succesfull
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
       // Log.d(TAG, "onInitializationSuccess: provider"+provider);
       // Log.d(TAG, "onInitializationSuccess: youtubePlayer"+youTubePlayer.toString());
       // Log.d(TAG, "onInitializationSuccess:  provider is"+provider.getClass().toString());
        //this will show the message on screen
        Toast.makeText(this,"Intialization of YouTubePlayer is Succesful",Toast.LENGTH_LONG).show();
       //if Restored from previous State play video from same time
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        if(!wasRestored)
        {
            youTubePlayer.loadVideo(YOUTUBE_VIDEO_ID);
        }

    }
    //this method implementations will get executed if initialization will failed
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        //we are going to pass sum data to show the error and help resolve it
        final int REQUEST_CODE = 1;
        if (youTubeInitializationResult.isUserRecoverableError())
        {
            // if error is user Recoverable then getErrorDialog will get Called
            //and will pass the context and Request Code
            //which will be shown on the screen
            youTubeInitializationResult.getErrorDialog(this,REQUEST_CODE).show();
        }
        else {
            //this will get executed if error is not user recoverable
            String errorMessage = String.format("there is an error ",youTubeInitializationResult.toString());
            Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show();
        }


    }
    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {
            Toast.makeText(Player.this,"video has playing",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onPaused() {
            Toast.makeText(Player.this,"video has paused",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStopped() {
            Toast.makeText(Player.this,"video has Stopped",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {
            Toast.makeText(Player.this,"Ad started",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {
            Toast.makeText(Player.this,"video has ended",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };


}

