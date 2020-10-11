package com.kumar.akshay.v_player;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class VerticalRecyclerAdapter extends RecyclerView.Adapter<VerticalRecyclerAdapter.MyViewHolder> {
    private static final String TAG = "RecyclerAdapter";
    private OnBucketNameCallback onBucketNameCallback;
    private ArrayList<BucketData> arrayList;

    /**
     * activity calling this Adapter has to implement this interface
     */
    interface OnBucketNameCallback {
        /**
         * this method will be called when user press the on Video Folder Name
         *
         * @param bucketID Folder Id which contains the videos
         */
        void onBucketName(String bucketID);
    }

    VerticalRecyclerAdapter(ArrayList<BucketData> arrayList, OnBucketNameCallback onBucketNameCallback) {
        this.arrayList = arrayList;
        this.onBucketNameCallback = onBucketNameCallback;
    }


    @NonNull
    @Override
    public VerticalRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view;
         //inflating view
           view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.verticle_recycler_file_view, parent, false);
          //returning view Holder
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VerticalRecyclerAdapter.MyViewHolder holder, int position) {
        final BucketData bucketData;
        Log.d(TAG, "onBindViewHolder: called position = " + position);
        if (arrayList.size() == 0) {
            //if received array list is null then show this
            holder.bucketNameView.setText(R.string.EMPTY_MESSAGE);
        } else {
            //if array list contains data
            //get object/bucket name from arraylist by giving position
            bucketData = arrayList.get(position);
            if (bucketData != null) {
                //if bucketdata is not empty
                //create view
                holder.bucketNameView.setText(String.format("%s\n\n%s", bucketData.getBucket_Name(), bucketData.getDate_modified()));
                //creating container listeners
                holder.verticalLayoutContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick: ID = " + bucketData.getBucket_ID());
                        //calling bucket method passing bucket name
                        onBucketNameCallback.onBucketName(bucketData.getBucket_ID());

                    }
                });
            }
        }

    }


    @Override
    public int getItemCount() {
        //if arraylist is empty at least return 1 to show the empty view
        //if arraylist is not empty then return the size of the arraylist
        if (arrayList == null || arrayList.size() == 0) {
            return 1;
        } else {
            return arrayList.size();
        }
    }

    ArrayList<BucketData> swapList(ArrayList<BucketData> newArrayList) {
        Log.d(TAG, "swapList: newArrayList size = " + newArrayList.size());
        //if data change in arrylist then notify
        arrayList = newArrayList;
        notifyDataSetChanged();
        Log.d(TAG, "swapList: notified");


        return arrayList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        //create view holders
        private ConstraintLayout verticalLayoutContainer;
        private LinearLayout container;
        private TextView bucketNameView;
        private ImageView folderView;

        MyViewHolder(View view) {
            super(view);

                verticalLayoutContainer = (ConstraintLayout) view.findViewById(R.id.verticalLayoutContainer);
                bucketNameView = (TextView) view.findViewById(R.id.bucketNameView);
                folderView = (ImageView) view.findViewById(R.id.folderView);


        }
    }
}


