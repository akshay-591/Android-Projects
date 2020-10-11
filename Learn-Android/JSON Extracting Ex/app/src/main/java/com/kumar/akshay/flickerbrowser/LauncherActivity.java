package com.kumar.akshay.flickerbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

public class LauncherActivity extends BaseActivity {
    private SearchView searchV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        searchV = (SearchView)findViewById(R.id.searchV);

        searchV.setIconified(false);
        searchV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query!=null&&!query.equalsIgnoreCase("")) {
                    Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                    intent.putExtra(FLICKR_QUERY, query);
                    startActivity(intent);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }
}
