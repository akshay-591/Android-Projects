package com.kumar.akshay.flickerbrowser;


import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class DownloadData  {
    private static final String TAG = "DownloadData";

     String downloader(String Url) {
        Log.d(TAG, "downloader: starts");
        URL url ;
        HttpURLConnection conn = null;
        BufferedReader br = null;
        StringBuilder data = new StringBuilder();
        try{
            url = new URL(Url);
            conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            int responsecode = conn.getResponseCode();
            Log.d(TAG, "operation: Response Code = " + responsecode);
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                data.append("\n").append(line);
            }
            //Log.d(TAG, "downloader: jason file  = "+data.toString());
            Log.d(TAG, "downloader:ends with no exception ");
            return data.toString();
        } catch (Exception ref) {
            Log.e(TAG, "downloader: Error" + ref.getMessage());
            return null;
        } finally {
            try {
                conn.disconnect();
                br.close();
            } catch (Exception e) {
                Log.d(TAG, "downloader: IO Error finally block " + e.getMessage());
                e.printStackTrace();
            }
        }


    }

}

