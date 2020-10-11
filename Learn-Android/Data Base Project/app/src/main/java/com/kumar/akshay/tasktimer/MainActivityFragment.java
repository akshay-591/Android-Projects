package com.kumar.akshay.tasktimer;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainActivityFragment extends Fragment implements LoaderDatabaseQuery.OnQueryDownload {


    private static final String TAG = "BlankFragment";
    private final int LOADER_ID = 0;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    public MainActivityFragment() {
        Log.d(TAG, "BlankFragment: starts");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: starts");
        super.onActivityCreated(savedInstanceState);
        LoaderDatabaseQuery databaseQuery = new LoaderDatabaseQuery( this,getContext(),LoaderManager.getInstance(this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter = new RecyclerAdapter(null, (RecyclerAdapter.onTaskButton) getActivity());
        recyclerView.setAdapter(recyclerAdapter);



        return view;
    }

    @Override
    public void OnQueryAvailabel(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "OnQueryAvailabel: starts");
        recyclerAdapter.swapCursor(data);
        int count =recyclerAdapter.getItemCount();
    }

    @Override
    public void queryLoaderReset(@NonNull Loader<Cursor> loader) {
    recyclerAdapter.swapCursor(null);
    }
}



