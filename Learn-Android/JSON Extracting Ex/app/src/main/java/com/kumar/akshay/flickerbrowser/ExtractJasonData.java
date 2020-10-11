package com.kumar.akshay.flickerbrowser;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExtractJasonData extends AsyncTask<String, Void, ArrayList<DataEntry>> {
    interface DataAvailable {
        void onDataAvailable(ArrayList<DataEntry> data);
    }

    private static final String TAG = "ExtractJasonData";
    private ArrayList<DataEntry> arrayList;
    private String baseUrl;
    private String language;
    private boolean matchAll;
    private final DataAvailable callBack;

    ExtractJasonData(DataAvailable callBack, String baseUrl, String language, boolean matchAll) {
        this.baseUrl = baseUrl;
        this.language = language;
        this.matchAll = matchAll;
        this.callBack = callBack;
    }

    @Override
    protected ArrayList<DataEntry> doInBackground(String... strings) {
        Log.d(TAG, "doInBackground: starts");
        //creating URl
        if (strings[0] == null) {
            return null;
        }
        String finalUrl = createUrl(strings[0], language, matchAll);
        //downloading data
        DownloadData DD = new DownloadData();
        String jsonData = DD.downloader(finalUrl);
        if (jsonData==null){
            return null;
        }
        //parsing or extracting data
        ArrayList<DataEntry> dataEntries = parseJsonData(jsonData);

        Log.d(TAG, "doInBackground: ends");
        //returning ArrayList
        return dataEntries;
    }

    @Override
    protected void onPostExecute(ArrayList<DataEntry> dataEntries) {
        Log.d(TAG, "onPostExecute: starts");
        if (dataEntries != null) {
            callBack.onDataAvailable(dataEntries);
        }
        Log.d(TAG, "onPostExecute: ends");

    }


    private String createUrl(String criteria, String lang, boolean matchAll) {
        Log.d(TAG, "createUrl: starts");
        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("tags", criteria)
                .appendQueryParameter("language", lang)
                .appendQueryParameter("tagmode", matchAll ? "All" : "Any")
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1").build();
        Log.d(TAG, "createUrl: ends");
        return uri.toString();


    }


    private ArrayList<DataEntry> parseJsonData(String data) {

        Log.d(TAG, "parseJsonData: starts");
        if (data == null) {
            return null;
        }
        arrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < itemArray.length(); i++) {
                JSONObject insideObject = itemArray.getJSONObject(i);
                String title = insideObject.getString("title");
                String author = insideObject.getString("author");
                String authorId = insideObject.getString("author_id");
                JSONObject media = insideObject.getJSONObject("media");
                String link = media.getString("m");
                String image = link.replaceFirst("_m", "_b");
                String tags = insideObject.getString("tags");
                DataEntry dE = new DataEntry(title, author, authorId, link, tags, image);
                //Log.d(TAG, "parseJsonData: Show image = " + dE.toString());
                arrayList.add(dE);
            }
            Log.d(TAG, "parseJsonData: ends with no exception");
            return arrayList;
        } catch (JSONException ref) {
            Log.d(TAG, "parseJsonData: Error " + ref.getMessage());
            Log.d(TAG, "parseJsonData: ends with exception");
            return null;
        }


    }


}
