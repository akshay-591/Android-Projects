package com.kumar.akshay.tasktimer;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * TaskContract class  contain Column class as a inner class
 * This class will be use to define Constant of the Task Table Columns Name and Their Input Type
 * so that These Constant can be Used anywhere in the app and save time to writing these Columns name again and again
 * also these Constant save from making error.
 *
 */

public class TaskContract {
    //Defining Table Name
    static final String TABLE_NAME="Tasks";


    //Task fields or Column
    public static class Columns {
        public static final String _id = BaseColumns._ID;
        public static final String TASK_NAME= "Name";
        public static final String TASKS_DESCRIPTION ="Description";
        public static final String TASKS_SORTORDER="SortOrder";

        //Declaring its constructor Private so that object of this class cant be created
        private Columns() {
            //private contructor to prevent instantiation
        }
    }

    /**
     *
     * The Uri to access the Taks Table
     */
    public static final Uri CONTENT_URI=Uri.withAppendedPath(AppProvider.CONTENT_AUTHORITY_URI,TABLE_NAME);
    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd."+AppProvider.CONTENT_AUTHORITY+"."+TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."+AppProvider.CONTENT_AUTHORITY+"."+TABLE_NAME;


    public static long getTaskId(Uri uri){
      return  ContentUris.parseId(uri);
    }

    static Uri buildTaskUri(long taskId){
        return ContentUris.withAppendedId(CONTENT_URI,taskId);
    }

}
