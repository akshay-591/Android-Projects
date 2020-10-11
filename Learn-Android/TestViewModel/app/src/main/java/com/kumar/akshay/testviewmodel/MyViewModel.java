package com.kumar.akshay.testviewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private static final String TAG = "MyViewModel";
    private String newData="";

    public void setData(String data) {
        Log.d(TAG, "setData: starts");
        newData+= data+"\n";
    }


    public String getData() {
        Log.d(TAG, "getData: starts");
        return newData;
    }


    @Override
    protected void onCleared() {
        Log.d(TAG, "onCleared: starts");
        super.onCleared();
        Log.d(TAG, "onCleared: ends");
    }
}
