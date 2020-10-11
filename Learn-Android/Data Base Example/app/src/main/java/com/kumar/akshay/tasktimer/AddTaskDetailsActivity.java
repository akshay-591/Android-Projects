package com.kumar.akshay.tasktimer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * this activity will get called by the MainActivity when the phone is in one- pane mode means in
 * portrait mode and add_task menu Item or edit Button gets selected.
 */
public class AddTaskDetailsActivity extends AppCompatActivity implements AddTaskDetailFragment.OnSaveClicked,AppDialog.DialogEvents {
    private static final String TAG = "AddTaskDetailsActivity";
    private final int DIALOG_ID_TASK_DETAIL=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_task_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //receiving Intent
        Intent intent = getIntent();
        //getting serializable data
        Tasks tasks = (Tasks)intent.getSerializableExtra(Tasks.class.getSimpleName());

         // Bundle arguments = getIntent().getExtras();
         //creating Bundle object which needs to passed to fragment
         Bundle arguments = new Bundle();
         //adding the data to Bundle
         arguments.putSerializable(Tasks.class.getSimpleName(),tasks);

        AddTaskDetailFragment fragment1 = new AddTaskDetailFragment();
        fragment1.setArguments(arguments);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.addTaskDetail_activity,fragment1);
        transaction.commit();


    }

    @Override
    public void onSClicked() {
     finish();
    }


    @Override
    public void onPositive(int dialogID, Bundle args) {
        Log.d(TAG, "onPositive: called dialogId  = "+dialogID);
    }

    @Override
    public void onNegative(int dialogID, Bundle args) {
        Log.d(TAG, "onNegative: called dialogId "+ dialogID);
        switch (dialogID){
            case DIALOG_ID_TASK_DETAIL:
                finish();
                break;
            default:
                //do nothing
        }
    }

    @Override
    public void onCancelled(int dialogID, Bundle args) {
        Log.d(TAG, "onCancelled: called");
    }
    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: called");
        AppDialog dialog = new AppDialog();
        Bundle args = new Bundle();
        args.putInt(AppDialog.DIALOG_ID,DIALOG_ID_TASK_DETAIL);
        args.putString(AppDialog.DIALOG_MESSAGE,getString(R.string.Task_detail_activity_message));
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(),null);
        // super.onBackPressed();
    }
}
