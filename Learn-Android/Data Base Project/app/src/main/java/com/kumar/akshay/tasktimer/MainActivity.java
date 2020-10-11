package com.kumar.akshay.tasktimer;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.onTaskButton,
                                                               AddTaskDetailFragment.OnSaveClicked,
                                                               AppDialog.DialogEvents{
    private static final String TAG = "MainActivity";
    //whether or not the activity is in 2-pain mode (in case of phone in landscape mode or running on a tablet)
    private boolean mTwoPane = false;
    private final int DELETE_DIALOG_ID=1;
    private final int BACK_KEY_DIALOG_ID=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //activity_main also contain MainActivityFragment
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        checking if frame layout view got detected or not
        if it got detected means its in the landscape mode
         */
        if ((findViewById(R.id.task_detail_container )!= null)) {
            Log.d(TAG, "onCreate: container found phone in landscape mode");
            //for landscape mode
            mTwoPane=true;
        }
       /* //creating database
        /*AppDatabase database = AppDatabase.getInstance(this);
        //calling OnCreate of AppDatabase Class
        final SQLiteDatabase db = database.getReadableDatabase();

        //calling ContentResolver using getContentResolver() method instance
        ContentResolver resolver=getContentResolver();
        //Creating ContentValues object to store Values in Table
        ContentValues values = new ContentValues();
        //adding Values

        values.put(TaskContract.Columns.TASK_NAME,"Cycling");
        values.put(TaskContract.Columns.TASKS_DESCRIPTION,"Exercise");
        values.put(TaskContract.Columns.TASKS_SORTORDER, 0);

       Uri uri= resolver.insert(TaskContract.CONTENT_URI,values);

        //storing projections
       String[] projection ={TaskContract.Columns._id,
                              TaskContract.Columns.TASK_NAME,
                              TaskContract.Columns.TASKS_DESCRIPTION,
                              TaskContract.Columns.TASKS_SORTORDER};

        /*calling query() method of Content Resolver class which will call
        the query() method implementation from the Custom Content Provider class if Registered in manifest file

        Cursor cursor =resolver.query(TaskContract.CONTENT_URI,projection,null,null,TaskContract.Columns.TASK_NAME);


        if(cursor!=null){
            Log.d(TAG, "cursor is not null");
            Log.d(TAG, "onCreate: rows = "+cursor.getCount());
            while(cursor.moveToNext()){
                for (int i=0;i<cursor.getColumnCount();i++) {
                    Log.d(TAG, "onCreate "+cursor.getColumnName(i)+" = "+cursor.getString(i));
                }
                Log.d(TAG, "-----------------------");
            }
            cursor.close();

        }*/


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

        /*
        working according to which menu item is pressed
        case 1 For adding task
        case 2 For showing Duration
        case 3 For Showing details about app
        case 4 For settings
        case 5 For Generate report
         */

       switch(item.getItemId()) {

           case R.id.menumain_addTask:
               //calling taskEditRequest() method and passing null parameter
               taskEditRequest(null);
               break;
           case  R.id.menumain_duration:
               break;
           case R.id.menumain_about:
               break;
           case R.id.menumain_settings:
               break;
           case R.id.menumain_generate:
               break;
           default:
               break;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onEditClick(Tasks tasks) {

        //calling taskEditRequest () and Passing tasks object
        if(tasks!=null){
            taskEditRequest(tasks);
        }
    }

    @Override
    public void onDeleteClick(Tasks tasks) {
        Log.d(TAG, "onDeleteClick: called");

        AppDialog dialog = new AppDialog();
        Bundle arguments = new Bundle();
        arguments.putInt(AppDialog.DIALOG_ID,DELETE_DIALOG_ID);
        arguments.putString(AppDialog.DIALOG_MESSAGE,getString(R.string.message)+tasks.getId()+" "+tasks.getName()+" ?");
        arguments.putSerializable(Tasks.class.getSimpleName(),tasks);
        dialog.setArguments(arguments);
        dialog.show(getSupportFragmentManager(),null);
    }

    private void taskEditRequest(Tasks task){
        Log.d(TAG, "taskEditRequest: starts");
        //checking if phone is in landscape mode or not
        if(mTwoPane) {
            //if device is in landscape mode
            Log.d(TAG, "taskEditRequest: in 2 pain");

            //create object of AddTaskDetailFragment class object
            AddTaskDetailFragment addDetailFragment = new AddTaskDetailFragment();
            //creating Bundle object
            Bundle arguments = new Bundle();
            //using putSerializable() method adding the data and it key in the Bundle object
            arguments.putSerializable(Tasks.class.getSimpleName(), task);


            /**
             * Note:- when we going to receive this arguments we should remember that Bundle is containig the class name as key and data (in Serialize form)
             * which means when we are going to receive it at the other end using getArgumnets() methods this method going to pass the key at its data both
             * means if data is null that does not mean Bundle is also going to be null it will contain the key
                    */

           /* if(task!=null) {
                arguments.putSerializable(Tasks.class.getSimpleName(), task);
            }else
            {
                arguments=null;
            }*/
           //adding Bundle object to fragment
            addDetailFragment.setArguments(arguments);
            //calling Fragment Manager using getSupportFragmentManager() method
            FragmentManager fragmentManager = getSupportFragmentManager();
            //starting Transaction means connecting an activity to a fragment
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            /*
            remove the current fragment if any and show the new one using replace()
            method and passing as parameter, called fragment class object and its related Container means a layout (ex- frame layout) ID
            in that Container that fragment XMl file gets inflated.
             */
            transaction.replace(R.id.task_detail_container,addDetailFragment);
            // commit means start showing on the screen
            transaction.commit();

        }else{
            //if device in one pane mode means in potrait mode call the addTaskDetailActivity which in Result going to call the AddTaskDetail fragment
            Log.d(TAG, "taskEditRequest: in single pane");
            //create Intent object and pass the calling Activity object/Context and called Activity Name/class name
            Intent detainlIntent = new Intent(this, AddTaskDetailsActivity.class);
            //checking task object
            if (task!=null){
                //if tasks is not null means edit button is pressed
                //add the Extra means task object in Intent using putExtra() method
                detainlIntent.putExtra(Tasks.class.getSimpleName(),task);
                //start the activity
                startActivity(detainlIntent);
            }else {
                //if task is null means add_task menu item got pressed
                //start the activity without adding extras
                startActivity(detainlIntent);
            }
        }
    }

    /**
     *
     * this method will get called when save Button get pressed
     * and current fragment will be removed from container or its layout
     *
     */
    @Override
    public void onSClicked() {
        Log.d(TAG, "onSClicked: starts");

        //calling Fragment Manager
         FragmentManager fManager = getSupportFragmentManager();
         //finding Fragment which needs to be removed using its Container ID
         Fragment fragment = fManager.findFragmentById(R.id.task_detail_container);
         //checking if there is that present or not
         if(fragment!=null){
             //if fragment present remove it
             /*FragmentTransaction fTransaction = fManager.beginTransaction();
             fTransaction.remove(fragment);
             fTransaction.commit();*/

             //short for above code
             getSupportFragmentManager().beginTransaction().remove(fragment).commit();
         }

    }

    @Override
    public void onPositive(int dialogID, Bundle args) {
        Log.d(TAG, "onPositive: called dialogID = "+dialogID);
        /*
         * Case 1 : Delete Task  Button is Pressed.
         * Case 2 : Back key is pressed in Main Activity.
         */
        switch (dialogID){
            case DELETE_DIALOG_ID:
                Tasks tasks =(Tasks) args.getSerializable(Tasks.class.getSimpleName());
                if (tasks==null) throw new AssertionError("Task object is null");
                //else
                getContentResolver().delete(TaskContract.buildTaskUri(tasks.getId()),null,null);
                break;

            case BACK_KEY_DIALOG_ID:
                //finish activity
                finish();
                break;
            default:
                //do nothing
        }

    }

    @Override
    public void onNegative(int dialogID, Bundle args) {
        Log.d(TAG, "onNegative: called");

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
        args.putInt(AppDialog.DIALOG_ID,BACK_KEY_DIALOG_ID);
        if(mTwoPane) {
            AddTaskDetailFragment fragment = (AddTaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.task_detail_container);
            if (fragment!= null){
                args.putString(AppDialog.DIALOG_MESSAGE,getString(R.string.landscape_back_key_message));
            } else args.putString(AppDialog.DIALOG_MESSAGE, getString(R.string.back_key_message));
        }else args.putString(AppDialog.DIALOG_MESSAGE, getString(R.string.back_key_message));

        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(),null);
        //super.onBackPressed();
    }
}
