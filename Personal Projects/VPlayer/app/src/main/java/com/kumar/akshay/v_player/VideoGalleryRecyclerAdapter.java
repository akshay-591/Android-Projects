package com.kumar.akshay.v_player;


import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

class VideoGalleryRecyclerAdapter extends RecyclerView.Adapter<VideoGalleryRecyclerAdapter.HorizontalViewHolder> {
    private static final String TAG = "HorizontalRecyclerAdapt";
    private Context context;
    private Cursor cursor;
    private OnVideoListener onVideoListener;

    /**
     * this interface has to be implemented in the activity which is calling this adapter
     */
    interface OnVideoListener {
        void onVideoClickListener(Uri uri);
    }

    //constructor
    VideoGalleryRecyclerAdapter(Context context, Cursor cursor, OnVideoListener onVideoListener) {
        this.context = context;
        this.cursor = cursor;
        this.onVideoListener = onVideoListener;
    }

    @NonNull
    @Override
    public VideoGalleryRecyclerAdapter.HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");

        View view = LayoutInflater.from(context).inflate(R.layout.gallery_view, parent, false);

        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoGalleryRecyclerAdapter.HorizontalViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: position = " + position);
        if (cursor == null || cursor.getCount() == 0) {
            holder.videoName.setText(R.string.EMPTY_MESSAGE);
        } else {
            if (!cursor.moveToPosition(position)) {

                throw new IllegalStateException("Cursor unable to Move to the Position  = " + position);
            } else {
                //appending Uri with Video ID
               final Uri uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID)));
                holder.videoName.setText(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)));
                // loading image using third party Library Glide
                Glide.with(context)
                        .load(uri)
                        .apply(new RequestOptions().override(500,500))
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(holder.videoThumb);

                holder.galleryViewContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        onVideoListener.onVideoClickListener(uri);
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        if (cursor == null || cursor.getCount() == 0) {
            return 1;
        } else {
            return cursor.getCount();
        }
    }

    Cursor swapCursor(Cursor newCursor) {

        if (newCursor == cursor) {
            return null;
        }

        final Cursor oldCursor = cursor;
        cursor = newCursor;

        if (newCursor != null) {
            //notify change
            notifyDataSetChanged();
        } else {
            notifyItemRangeRemoved(0, getItemCount());
        }
        return oldCursor;
    }


    class HorizontalViewHolder extends RecyclerView.ViewHolder {
        private ImageView videoThumb;
        private TextView videoName;
        private ConstraintLayout galleryViewContainer;

        HorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            galleryViewContainer = (ConstraintLayout)itemView.findViewById(R.id.galleryViewContainer);
            videoThumb = (ImageView) itemView.findViewById(R.id.videoThumb);
            videoName = (TextView) itemView.findViewById(R.id.videoName);

        }
    }
}
