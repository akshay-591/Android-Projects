package com.kumar.akshay.flickerbrowser;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements ExtractJasonData.DataAvailable, RViewOnItemClickListener.onRecycleClickListener {
    private static final String TAG = "MainActivity";
    private String jsonUrl;
    private RviewAdapter rviewAdapter;
    private RecyclerView rView;
    private SearchView searchView;
    private  String tags;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activateToolbar(true);
        rView = (RecyclerView) findViewById(R.id.rView);
        rView.setLayoutManager(new GridLayoutManager(this, 2));
        RViewOnItemClickListener clickListener = new RViewOnItemClickListener(this, this, rView);
        rView.addOnItemTouchListener(clickListener);
        rviewAdapter = new RviewAdapter(new ArrayList<DataEntry>(), MainActivity.this);
        rView.setAdapter(rviewAdapter);
        Intent intent = getIntent();
        tags= (String) intent.getSerializableExtra(FLICKR_QUERY);
        executer(tags);
        Log.d(TAG, "onCreate: ends");


    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: starts");
        super.onResume();
        Log.d(TAG, "onResume: ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: starts");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.searchView).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(true);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit:  starts");
                if (query != null && !query.equals("")) {
                    executer(query);
                    Log.d(TAG, "onQueryTextSubmit: query = " + query);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        Log.d(TAG, "onCreateOptionsMenu: ends");
        return true;
    }


    @Override
    public void onDataAvailable(ArrayList<DataEntry> data) {
        Log.d(TAG, "onDataAvailable: starts");

        if (data != null) {
            Log.d(TAG, "onDataAvailable: adapter starts");

           rviewAdapter.loadNewData(data);
        }
        Log.d(TAG, "onDataAvailable: ends");

    }

    @Override
    public void onItemClicked(View view, int position) {
        Log.d(TAG, "onItemClicked: starts");

    }

    @Override
    public void onLongClick(View view, int position) {
        Log.d(TAG, "onLongPress: starts ");
        Intent intent = new Intent(this, FullView.class);
        /*calling puExtra() method
        this method will  serialize the data and then store that in map<T> collection object
        so as first parameter we are passing a string which will be the key and as second parameter
        we are passing the object of Data Entry class whose object are got marked for serialization using marking interface serializable
        so that serialized data of that object will get store as value and PHOTO_TRANSFER will be the key for that value*/
        intent.putExtra(PHOTO_TRANSFER, rviewAdapter.getPhoto(position));
        if (intent != null) {
            startActivity(intent);
        }


    }
    private void executer (String newQuery){
        jsonUrl = "https://api.flickr.com/services/feeds/photos_public.gne";
        ExtractJasonData extractJasonData = new ExtractJasonData(MainActivity.this, jsonUrl, "en-us", true);
        Log.d(TAG, "onResume: tags = " + newQuery);
        extractJasonData.execute(newQuery);
    }

}

