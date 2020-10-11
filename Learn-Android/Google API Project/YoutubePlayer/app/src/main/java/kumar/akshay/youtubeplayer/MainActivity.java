package kumar.akshay.youtubeplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //attaching View Ids
        Button player = (Button) findViewById(R.id.player);
        Button standAlone = (Button)findViewById(R.id.standAlone);
        //calling Listeners
        player.setOnClickListener(this);
        standAlone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //defining Intent object Reference
        Intent intent=null;
        //switch statement condition depends on which Button is clicked
        switch(view.getId()) {
            //case 1 id player Button is clicked
            case R.id.player:
                //referencing Player Activity
                intent = new Intent(this, Player.class);
                break;
                //case 2 If Stand Alone Button is Clicked
            case R.id.standAlone:
                //referencing StandAloneActivity Activity
                intent = new Intent(this, StandAloneActivity.class);
                break;
                //default do nothing
            default:
                //
        }
        //if intent is not equal to null start the Activity using Intent reference
        if (intent!=null)
        {
            startActivity(intent);
        }

    }
}
