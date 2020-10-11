package com.kumar.akshay.flickerbrowser;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

class RViewOnItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RViewOnItemClickListene";

    //defining interface so that Main Activity can know something is clicked and proceed further
    interface onRecycleClickListener {
        //method for one time click on View
        //receiving that View and its position
        void onItemClicked(View view, int position);

        //method for long press
        //receiving that View and its position
        void onLongClick(View view, int position);
    }

    //creating object reference to onRecycleClickListener and GestureDetector(compat)
    private final onRecycleClickListener listener;
    private final GestureDetectorCompat gDetector;

    //constructor and receiving context of Calling Activity, onRecycleClickListener, RecyclerView
    RViewOnItemClickListener(Context context, final onRecycleClickListener listener, final RecyclerView rView) {
        this.listener = listener;

        gDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            //overriding methods so that we can these events.
            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                View childView = rView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && listener != null) {

                    listener.onItemClicked(childView, rView.getChildAdapterPosition(childView));
                }
                return true;
                // return super.onSingleTapUp(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {

                View childView = rView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && listener != null) {
                    Log.d(TAG, "onLongPress: onLongClick");
                    listener.onLongClick(childView, rView.getChildAdapterPosition(childView));
                }

                //super.onLongPress(e);
            }
        });

    }
    /*overriding the methods on SimpleOnItemTouchListeners class so that we can respond to the onInterceptTouchEvent method to intercept
    all touch event happens on Recycler View*/

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, MotionEvent e) {

        /*if we return true that means we are saying to Android framework that we handled every Motion event passed by Android frameWork like one tap and scrolling etc
          and nothing needed to be done*/
        /*but if return false that means telling Android frameWork to take care of the events which we did not handled
          now the question is how we going to decide which events to deal with for that we use gesture detector ,it can tell what type of gesture is happening
             */
        /* so what we are going to do here is let this method (onInterceptTouchEvent()) to call and
         pass the event to Gesture Detector Method which want to handle and we will call the the appropriate method from there
         */
        if (gDetector != null) {
            //pass the MotionEvent(event happening on screen) to onTouchEvent method
            //TouchEvent will detect the event and call the appropriate method according to that
            //by default all methods return false.
            //to return true we need to override those method
            boolean result = gDetector.onTouchEvent(e);

            //returning the result either true or flase depends on if that event took cared of or not
            return result;

        }
        //else return false
        else {

            return false;
        }
        /* so the theory here is anything onTouchEvent can handle return True if anything id did not handel return false;*/
        //return super.onInterceptTouchEvent(rv, e);
    }


}
