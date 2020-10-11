package com.kumar.akshay.tasktimer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class AddTaskDetailFragment extends Fragment {
    private static final String TAG = "addtaskActivityFragment";
    /**
     * this interface will get the callback when save button gets clicked
     * so the function like removing the fragments can be done through this interface abstract method in calling Activity
     */
    interface OnSaveClicked{
        void onSClicked();
    }

    public enum FragmentEditMode  {EDIT, ADD};
    private EditText mTaskName;
    private EditText mDescription;
    private EditText mSortOrder;
    private ImageButton mSaveButton;
    private FragmentEditMode mMode;
    private OnSaveClicked mSaveListeners;

    //constructor
    public AddTaskDetailFragment(){
        Log.d(TAG, "addtaskActivityFragment1: contructor called");
    }


    @Override
    public void onAttach(@NonNull Context context) {
        /**
         * activity using this fragment should implements its interface
         */
        //get calling activity object reference
        Activity activity = getActivity();
 //checking if that activity contains OnSaveClicked interface implementation or not
        if(!(activity instanceof OnSaveClicked)){
            //if not then throw an exception
            throw new ClassCastException(activity.getClass().getSimpleName()+"activities using this interface should implements its interface");
        }
        //if yes cast that object reference to OnSaveClicked and assign it to mSaveListener
        mSaveListeners = (OnSaveClicked) activity;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        mSaveListeners = null;
        super.onDetach();
    }


// this method will create the View for and perform the action
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: starts");
        //inflating fragment_first2 layout
        View view = inflater.inflate(R.layout.fragment_first2, container, false);
        //attaching widgets to activity
        mTaskName = (EditText) view.findViewById(R.id.addedit_name);
        mDescription = (EditText) view.findViewById(R.id.addedit_Description);
        mSortOrder = (EditText) view.findViewById(R.id.addedit_Sortorder);
        mSaveButton = (ImageButton) view.findViewById(R.id.addedit_save);

        //Bundle arguments = getActivity().getIntent().getExtras();
        //getting Bundle if any passed by calling activity
        Bundle arguments = getArguments();


        final Tasks task;
//checking if Bundle is null or not
        if (arguments != null) {
            Log.d(TAG, "onCreateView: Retrieving task details ");
            //if Bundle is not null
            //get the serializable ddata using key and cast and store it in Tasks object
            task = (Tasks) arguments.getSerializable(Tasks.class.getSimpleName());

            //checking if Tasks object is null or not
            if (task != null) {
                /*
                if Tasks object is not null get the data like name, description and sortorder from that object and set those to EditText Views
                 */
                mTaskName.setText(task.getName());
                mDescription.setText(task.getDescriptiopn());
                String so = String.valueOf(task.getSortorder());
                mSortOrder.setText(so);
                //set the mMode to Edit Mode
                mMode = FragmentEditMode.EDIT;
            }else{
                //if task object is null set the mMode to the ADD mode
                mMode = FragmentEditMode.ADD;
            }
        } else {
            //if Bundle is null then set the mMode to ADD mode and task to null
            Log.d(TAG, "onCreateView: Task object is null");
            task = null;
            mMode = FragmentEditMode.ADD;
        }

        //save Button Click listener
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int so; //saving from conversion from int to string multiple times
                if(mSortOrder.length()>0){
                    //if sortorder length is greater than Zero then get it
                    so=Integer.parseInt(mSortOrder.getText().toString());

                }else {
                    //if sortorder EditText view length is less then Zero set so to 0
                    so = 0;
                }
                //calling Content Resolver
                ContentResolver resolver = getActivity().getContentResolver();
                //creating ContentValues object to store the new Values
                ContentValues values = new ContentValues();
                //switch will act according to mMode
                //case 1 : If Edit Button is pressed for Updating Task data
                //case 2 : if Add_new task menu Item is selected for adding new Task to the database

                switch (mMode){
                    case EDIT:
                        //values only will be edited if they are changed if EditText view will have the same name those Data will not going to be edited in value object
                        if(!mTaskName.getText().toString().equals(task.getName())) {
                            values.put(TaskContract.Columns.TASK_NAME, mTaskName.getText().toString());
                        }
                        if(!mDescription.getText().toString().equals(task.getDescriptiopn())) {
                            values.put(TaskContract.Columns.TASKS_DESCRIPTION, mDescription.getText().toString());
                        }
                        if(so!=task.getSortorder()) {
                            values.put(TaskContract.Columns.TASKS_SORTORDER, so);
                        }
                        if(values.size()!=0) {
                            int rowaffected = resolver.update(TaskContract.buildTaskUri(task.getId()), values, null, null);
                            Log.d(TAG, "onClick: row Updated = " + rowaffected);
                        }
                        break;

                    case ADD:
                        //new Task should contain at least Name without defining name Task will not going to added
                        if(mTaskName.length()>0){
                            Log.d(TAG, "onClick: adding new task");
                            values.put(TaskContract.Columns.TASK_NAME,mTaskName.getText().toString());
                            values.put(TaskContract.Columns.TASKS_DESCRIPTION,mDescription.getText().toString());
                            values.put(TaskContract.Columns.TASKS_SORTORDER,so);
                            resolver.insert(TaskContract.CONTENT_URI,values);
                        }else {
                        }

                        break;
                }
                Log.d(TAG, "onClick: Done Editing");
                Log.d(TAG, "onClick: exiting ");
                //calling onSClicked() method after save button is pressed
                if(mSaveListeners!=null){
                    mSaveListeners.onSClicked();
                }
            }

        });

        //returning View
        return view;

}
}
