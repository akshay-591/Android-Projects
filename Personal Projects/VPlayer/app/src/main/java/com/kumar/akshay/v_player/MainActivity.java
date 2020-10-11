package com.kumar.akshay.v_player;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.kumar.akshay.v_player.databinding.ActivityMainBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements CustomLoader.onQueryUpdated,
        VerticalRecyclerAdapter.OnBucketNameCallback {

    /*
     * creating Variables and Object References
     */
    private static final String TAG = "MainActivity";
    private static final int READ_STORAGE_REQUEST_CODE = 1;

    private int hasPermission;

    public static final int VERTICAL_LOADER_ID = 1;
    public static final int HORIZONTAL_LOADER_ID = 2;

    private ActivityMainBinding mBindView;

    private VerticalRecyclerAdapter verticalRecyclerAdapter;

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        //inflating Views
        mBindView = ActivityMainBinding.inflate((getLayoutInflater()));
        view = mBindView.getRoot();
        setContentView(view);
        //attaching toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //checking Permission
        hasPermission = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);

        //if permission not granted call requestPermission()
        if (hasPermission != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, READ_STORAGE_REQUEST_CODE);
        }
        //attach RecyclerView and set its Layout Manager and adapter

        mBindView.rView.setLayoutManager(new LinearLayoutManager(this));
        verticalRecyclerAdapter = new VerticalRecyclerAdapter(new ArrayList<BucketData>(), this);
        mBindView.rView.setAdapter(verticalRecyclerAdapter);

    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: called");
        super.onResume();
        /*
         * Creating Loader only if Permission is granted otherwise not
         *
         */
        //checking permission on onResume Method because onCreate Method will not be called again after Permission dialog box appear on the screen
        hasPermission = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);

        //if permission is granted create Loader
        if (hasPermission == PERMISSION_GRANTED) {
            Log.d(TAG, "onResume: permission Granted");
            new CustomLoader(this, this, LoaderManager.getInstance(this), VERTICAL_LOADER_ID);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_STORAGE_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] != PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult: permission denied ");
                    //creating snackbar to ask permission from user
                    Snackbar.make(view, R.string.app_permission_Message, Snackbar.LENGTH_INDEFINITE).setAction("Grant Permission", new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissions[0])) {
                                //if user did not checked the don ask box
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{READ_EXTERNAL_STORAGE}, READ_STORAGE_REQUEST_CODE);
                            } else {
                                //if user checked the don ask box take that idiot to settings
                                //creating intent
                                Intent intent = new Intent();
                                //calling app details settings activity
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                //passing calling activity URI Package name
                                Uri uri = Uri.fromParts("package", MainActivity.this.getPackageName(), null);
                                intent.setData(uri);
                                MainActivity.this.startActivity(intent);
                            }
                        }
                    }).show();


                }
                //if permission granted then show Toast message
                else Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onQueryAvailable(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onQueryAvailable: called");
        if (data != null) {
            switch (loader.getId()) {
                case VERTICAL_LOADER_ID:
                    String ID = "";
                    BucketData bucketData;
                    ArrayList<BucketData> arrayList = new ArrayList<>();
                    /*
                     *after activity get stopped (means we either press home or recent button
                     * or lock screen button) and resumed again cursor still remain at its last position since loader did not reset
                     * for that we first check if cursor is at -1 position means position above the first row of the table or not
                     * if cursor is not there then we set its position to -1 and move forward.
                     */

                    if (data.getPosition() != -1) {
                        data.moveToPosition(-1);
                    } else {/*do nothing and continue*/}

                    while (data.moveToNext()) {
                        Log.d(TAG, "onQueryAvailable: In loop");
                        // personal code
                        if (!data.getString(data.getColumnIndex(MediaStore.Video.Media.BUCKET_ID)).equals(ID)) {
                            ID = data.getString(data.getColumnIndex(MediaStore.Video.Media.BUCKET_ID));

                            bucketData = new BucketData(data.getString(data.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)),
                                    ID,
                                    data.getLong(data.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED)));
                            arrayList.add(bucketData);

                        }
                    }
                    Log.d(TAG, "onQueryAvailable: arrayList size = " + arrayList.size());
                    //passing array list to RecylerView adapter
                    verticalRecyclerAdapter.swapList(arrayList);
                    break;

                default:
                    //do nothing
            }
        } else Log.d(TAG, "onQueryAvailable: Data is null");

    }


    @Override
    public void onQueryReset(@NonNull Loader<Cursor> loader) {
        Log.d(TAG, "onQueryReset: called");
        //resetting loader
        switch (loader.getId()) {
            case VERTICAL_LOADER_ID:
                verticalRecyclerAdapter.swapList(null);
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBucketName(String bucketID) {
        Log.d(TAG, "onBucketName: called with Bucket ID = " + bucketID);
        //calling the Video Gallery activity and passing the Bucket name to it
        Intent intent;
        if (bucketID != null) {
            intent = new Intent(this, Video_Gallery.class);
            intent.putExtra(BucketData.class.getSimpleName(), bucketID);
            startActivity(intent);
        } else {
            throw new IllegalStateException("bucket ID is Null");
        }
    }

}
