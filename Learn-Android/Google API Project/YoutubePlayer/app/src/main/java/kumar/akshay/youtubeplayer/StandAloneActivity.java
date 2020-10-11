package kumar.akshay.youtubeplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

public class StandAloneActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stand_alone);
        //defining Intent object Reference
        Button playVideo =(Button)findViewById(R.id.playVideo);
        Button playPlaylist =(Button)findViewById(R.id.playPlaylist);
        //calling Listeners
        playVideo.setOnClickListener(this);
        playPlaylist.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //defining Intent object Reference
        Intent intent = null;
        //switch statement condition depends on which Button is clicked
        switch (v.getId())
        {
            //case 1 id Play Video Button is clicked
            case R.id.playVideo:
                //storing returned intent Reference By createVideoIntent() method of YouTubeStandAlonePlayer class in YouTubePlayer API.
                //passing API key and Video Link
                intent = YouTubeStandalonePlayer.createVideoIntent(this,Player.GOOGLE_API_KEY,Player.YOUTUBE_VIDEO_ID,0,true,true);
                break;
            //case 1 id Play Playlist Button is clicked
            case R.id.playPlaylist:
                //storing returned intent Reference By createPlaylistIntent() method of YouTubeStandAlonePlayer class in YouTubePlayer API.
                //passing API key and Playlist Link
                intent = YouTubeStandalonePlayer.createPlaylistIntent(this,Player.GOOGLE_API_KEY,Player.YOUTUBE_PLAYLIST);
                break;
                //default do nothing
            default:

        }
        //if intent is not equal to null start the Activity using Intent reference
        if (intent!= null){
            startActivity(intent);
        }

    }
}
