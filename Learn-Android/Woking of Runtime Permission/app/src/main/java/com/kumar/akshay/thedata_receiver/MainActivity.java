package com.kumar.akshay.thedata_receiver;

import static android.Manifest.permission.READ_CONTACTS;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView contactView;
    private static final int REQUEST_CODE_READ_CONTACTS = 1;
    private static boolean READ_CONTACTS_GRANTED = false;
    private FloatingActionButton fab = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        contactView = (ListView) findViewById(R.id.contactView);

        /* framework will self check the Read contact permission by calling the checkSelfPermission() method of Context class
        and get the  response code if code is 0 Permission is already granted if code is -1 permission is denied or not granted. */
        //as a parameter we are passing context and the Permission Name to check

        int hasReadContactsPermission = ContextCompat.checkSelfPermission(this, READ_CONTACTS);
        Log.d(TAG, "onCreate: CheckSelfPermission = " + hasReadContactsPermission);

        //condition if permission is granted
        if (hasReadContactsPermission == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onCreate: permission granted");
            READ_CONTACTS_GRANTED = true;
        } else {
            /*
            if permission is not granted then call the requestPermission() method of Activity class
            as a parameter of this method we have to pass Activity Context , String array
            containing Permissions and there Request Code. This method will show a
            Context menu (Floating menu) on Screen which will be running in the Background
            thread and ask the user to either allow the app to read the Contact on Deny it
            */

            Log.d(TAG, "onCreate: permission Denied");
            ActivityCompat.requestPermissions(this, new String[]{READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
        }

                /*if permission is granted to Read Contacts of the user phone then we will
                use the Content provider and content Resolver to Read and write the data
                here Content Provider will get the data from Data source for the client and Content Resolver will act like Mediator
                between the User and the Content Provider it will take the Message from client or user and pass that Message to the Content Provider
                so that Provider will go the Relevant Data source to get the Relevant data. */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "fab onClick: Starts");
                if (ContextCompat.checkSelfPermission(MainActivity.this,READ_CONTACTS)==PackageManager.PERMISSION_GRANTED
                ) {
                    //if permission is Granted
                    //Storing the table column name from User or client so that it can pass to Provider to find Relevant data Source
                    // Here we are storing Built in contacts Display Name Column in the projection array
                    String[] projection = {ContactsContract.Contacts.DISPLAY_NAME_PRIMARY};

                    // now calling the Content Resolver by using getContentResolver() method
                    ContentResolver resolver = getContentResolver();

                /*calling query () method of Content Resolver and Passing the Parameter which is Content Uri (basically Provider address)
                  Second-  is the projection (means what we need from that Data  source)
                  Third- selection clause a type of filter(just like Where Clause in SQL)
                  Fourth is Seletcionargs as null
                  Fifth is Order in which they will appear
                  this method will return the data to cursor an place it in above row of the data             */
                    Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, projection,
                            null, null, ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);

                    if (cursor != null) {
                        //if cursor is not null create a collection object
                        ArrayList<String> arrayList = new ArrayList<>();
                        Log.d(TAG, "onClick: Cursor is not null");

                        //starting loop and moving the cursor to the next row
                        while (cursor.moveToNext()) {

                            Log.d(TAG, "onClick: inside while loop");
                            //if condition passed then extract the information
                            //getting the column index where Name is Stored
                            int index = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
                            Log.d(TAG, "onClick: index" + index);
                            //getting the string name from that column
                            String name = cursor.getString(index);
                            // adding that name to collection object
                            arrayList.add(name);
                        }

                        //after collection is done close the cursor
                        cursor.close();

                        //call the Array Adapter to show the data in ListView
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.data_view, arrayList);
                        contactView.setAdapter(arrayAdapter);
                    }


                } else {
                    //if permission is not granted
                    Snackbar.make(view, "This app need Read contact Permission to show the Contact Details", Snackbar.LENGTH_INDEFINITE).
                            setAction("Grant Permission", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,READ_CONTACTS)) {
                                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
                                    }else{
                                        //if user denied the the permission and check the Dont ask again check box
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package",MainActivity.this.getPackageName(),null);
                                        Log.d(TAG, "snackbar onClick: Uri =  "+uri.toString());
                                        intent.setData(uri);
                                        MainActivity.this.startActivity(intent);
                                    }
                                }
                            }).show();
                }

            }

        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: starts");
        switch (requestCode) {
            case REQUEST_CODE_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult: granted");
                    READ_CONTACTS_GRANTED = true;
                } else {

                    Log.d(TAG, "onRequestPermissionsResult: denied");
                }
                //enable the fab button only if permission is granted
                //fab.setEnabled(READ_CONTACTS_GRANTED);
            }

        }
        Log.d(TAG, "onRequestPermissionsResult: ends");
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
}
