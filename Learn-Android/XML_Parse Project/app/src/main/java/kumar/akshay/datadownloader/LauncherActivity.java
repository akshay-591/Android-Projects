package kumar.akshay.datadownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LauncherActivity extends AppCompatActivity implements View.OnClickListener {
static String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Button apps = findViewById(R.id.apps);
        Button songs = findViewById(R.id.songs);
        songs.setOnClickListener(this);
        apps.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch(v.getId()){
            case R.id.apps:
                URL="http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml";
                intent = new Intent(this,MainActivity.class);
                break;
            case R.id.songs:
                URL="http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml";
                intent = new Intent(this,MainActivity.class);
                break;
            default:
                //nothing

        }
        if (intent!=null)
        {
            startActivity(intent);
        }
    }
}
