package com.kumar.akshay.flickerbrowser;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class FullView extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);
        activateToolbar(true);
        Intent intent = getIntent();
        /*getSerializableExtra method will get the value or Data
        using PHOTO_TRANSFER as a key and cast or we can say De-Serialize it as Data Entry object
         */
        DataEntry dataEntry = (DataEntry)intent.getSerializableExtra(PHOTO_TRANSFER);
        if (dataEntry!=null) {
            ImageView fullView = (ImageView) findViewById(R.id.fullView);
            Picasso.get().load(dataEntry.getImages())
                    .error(R.drawable.ic_launcher_foreground)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(fullView);
        }

    }
}
