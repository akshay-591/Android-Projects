package com.kumar.akshay.tasktimer;

import android.content.ContentResolver;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.taskViewHolder> {
    private static final String TAG = "RecyclerAdapter";
    private Cursor cursor;
    private onTaskButton mlistener;

    /**
     * interface used for callback by Edit button
     */
    interface onTaskButton {
        /**
         * method which is called by Edit button when clicked
         * @param tasks object containing the information of button position tasks information
         */
       void onEditClick(Tasks tasks);
       void onDeleteClick(Tasks tasks);
    }

    /**
     * RecyclerAdapter constructor
     * @param cursor
     * @param listener
     */
    RecyclerAdapter(Cursor cursor, onTaskButton listener){
        this.cursor=cursor;
        mlistener =listener;
    }

    @NonNull
    @Override
    public taskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating view (task_view_list xml file)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_view_list,parent,false);
        //calling taskViewHolder for the widgets
        taskViewHolder holder = new taskViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull taskViewHolder holder, int position) {

        //checking cursor
        if (cursor == null || cursor.getCount() == 0) {
            //if cursor is equal to null or equal to zero
            holder.taskName.setText("No task to show");
            holder.taskDescription.setText("No description to show");
            //disable the button
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
        } else {
            //if cursor is not equal to null
            if (!cursor.moveToPosition(position)) {
                //move to to position (ask by adapter on Recycle view)
                //if there is now row at that position throw an exception
                Log.d(TAG, "onBindViewHolder:");
                throw new IllegalStateException("No data to show cusrsor can not move to " + position);
            } else {
                //if there is row perform these tasks
                Log.d(TAG, "onBindViewHolder: showing data");

                //store the current position row details from cursor
                long Id =cursor.getLong(cursor.getColumnIndex(TaskContract.Columns._id));
                String name= cursor.getString(cursor.getColumnIndex(TaskContract.Columns.TASK_NAME));
                String description =cursor.getString(cursor.getColumnIndex(TaskContract.Columns.TASKS_DESCRIPTION));
                int sortOrder = cursor.getInt(cursor.getColumnIndex(TaskContract.Columns.TASKS_SORTORDER));

                // create the Tasks object and store the current position row details from cursor
                final Tasks tasks = new Tasks(Id,name,description,sortOrder);

                //show the same details in Views
                holder.taskName.setText(name);
                holder.taskDescription.setText(description);
                //enable the Button
                holder.editButton.setVisibility(View.VISIBLE);
                holder.deleteButton.setVisibility(View.VISIBLE);

                // call Button listeners
                final View.OnClickListener buttonListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //work according to button ID
                        switch (v.getId()){
                            //Case 1 if edit Button is pressed
                            case R.id.editButton:
                                Log.d(TAG, "onClick: editing called");
                                //call onEditClck() methods and pass the Tasks object
                                 mlistener.onEditClick(tasks);
                                break;

                                //Case2 if deleteButton is pressed
                            case R.id.deleteButton:
                                Log.d(TAG, "onClick: item delete called");
                                /*//get ID of the task which neede to delete form Tasks object
                                //create searchCriteria for WHERE claus
                                String selectionCriteria= TaskContract.Columns._id + " = "+tasks.getId();

                                // call ContentResolver
                                ContentResolver resolver = v.getContext().getContentResolver();
                                //call delete Query and pass the URI and WHERE claus Criteria
                                resolver.delete(TaskContract.CONTENT_URI,selectionCriteria,null);*/
                                mlistener.onDeleteClick(tasks);
                                break;
                            default:
                                //do nothing
                        }
                    }
                };

                //set the listeners
                holder.editButton.setOnClickListener(buttonListener);
                holder.deleteButton.setOnClickListener(buttonListener);
            }
        }
    }


    @Override
    public int getItemCount() {

       if(cursor==null||cursor.getCount()==0){
           return 1;
       }else{
           return cursor.getCount();
       }

    }

    /**
     * swap in  new Cursor return old Cursor
     *The Returned Old cursor is not closed
     *
     * @param newCursor The new cursor to be Used
     * @return  Return the Previously set Cursor or null there wasn't one
     * if the given new cursor is the same instance as the previously set
     * cursur null is also returned
     */
    Cursor swapCursor(Cursor newCursor){

        if (newCursor==cursor){
            return null;
        }

        final Cursor oldCursor = cursor;
        cursor=newCursor;

        if(newCursor!=null){
            //notify change
            notifyDataSetChanged();
        }else {
            notifyItemRangeRemoved(0,getItemCount());
        }
        return oldCursor;


    }

   static class taskViewHolder extends RecyclerView.ViewHolder  {
        private TextView taskName,taskDescription;
        private Button editButton, deleteButton;
        taskViewHolder(View v){
            super(v);
            taskName=(TextView)v.findViewById(R.id.taskName);
            taskDescription=(TextView)v.findViewById(R.id.taskDescription);
            editButton =(Button)v.findViewById(R.id.editButton);
            deleteButton=(Button)v.findViewById(R.id.deleteButton);

        }

    }
}
