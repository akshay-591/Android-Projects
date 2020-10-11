package com.kumar.akshay.flickerbrowser;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class RviewAdapter extends RecyclerView.Adapter<RviewAdapter.RviewViewholder> {
    private static final String TAG = "RviewAapter";
    private ArrayList<DataEntry> photoList;
    private Context context;

    public RviewAdapter(ArrayList<DataEntry> newPhotoList, Context context) {
        Log.d(TAG, "RviewAapter: constructore starts");
        this.photoList = newPhotoList;
        this.context = context;
        Log.d(TAG, "RviewAapter: ends");
    }
    static class RviewViewholder extends RecyclerView.ViewHolder {
        private static final String TAG = "RviewViewholder";
        ImageView thumbnail;
        TextView title = null;

        //constructor
        RviewViewholder(View itemView) {
            super(itemView);
            Log.d(TAG, "RviewViewholder: starts");
            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.textView);
            Log.d(TAG, "RviewViewholder: ends");
        }

    }

    @Override
    public RviewViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: new View Requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.json_view, parent, false);
        Log.d(TAG, "onCreateViewHolder: ends");
        return new RviewViewholder(view);
    }



    @Override
    public void onBindViewHolder(RviewViewholder holder, int position) {
        DataEntry dataEntry = photoList.get(position);
        Picasso.get().load(dataEntry.getLink())
                .error(R.drawable.ic_launcher_foreground)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.thumbnail);
        holder.title.setText(dataEntry.getTitle());

    }

    @Override
    public int getItemCount() {
        /*if (photoList!=null&&photoList.size()!=0){
            return photoList.size();
        }
        else {
            return 0;
    }*/
        //we can also write this as

        return ((photoList != null) && (photoList.size() != 0) ? photoList.size() : 0);

    }

    DataEntry getPhoto(int position) {

        if (photoList != null && photoList.size() != 0) {
            return photoList.get(position);

        } else {
            return null;
        }
    }
     void loadNewData(ArrayList<DataEntry> newdata){
         Log.d(TAG, "loadNewData: starts");
          this.photoList=newdata;
         notifyDataSetChanged();

    }


}
