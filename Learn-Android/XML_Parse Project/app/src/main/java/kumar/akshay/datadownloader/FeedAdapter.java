package kumar.akshay.datadownloader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class FeedAdapter extends ArrayAdapter {
    //declaring instance Variable
    //private static final String TAG = "FeedAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private final ArrayList<FeedEntry> application;
    private Context mContext;

    //initializing constructor
    FeedAdapter(Context context, int resource, ArrayList<FeedEntry> application) {
        //calling super constructor
        super(context, resource);
        // initializing variable
        this.mContext=context;
        this.layoutResource = resource;
        this.application = application;
        this.layoutInflater = LayoutInflater.from(context);
    }

    //overriding getCount method
    @Override
    public int getCount() {
        return application.size();
    }

    //overriding getView method
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // declaring variable
        ViewHolder viewHolder;
        //checking if there is view for re-use or not
        if (convertView == null) {
            //if no then inflate resource file
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            //attaching widgets Ids to sctivity varibale
            viewHolder = new ViewHolder(convertView);
            // storing that information in Tag object
            convertView.setTag(viewHolder);
        } else {
            //if convertView is not null
            //get the Ids information from Tag object
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //attaching object reference from collection using get() method and passing position of the object.
        FeedEntry currentapp = application.get(position);
        //setting text in  the Views
        viewHolder.tvName.setText(currentapp.getName());
        viewHolder.tvArtist.setText(currentapp.getArtist());
        viewHolder.tvSummary.setText(currentapp.getSummary());
        Picasso.get()
                .load(currentapp.getImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(viewHolder.tvImage);
       // viewHolder.tvImage.setImageBitmap(currentapp.getImage());
        //returning view
        return convertView;
    }

    // this class is used for attaching activity variable with widgets Ids
    private class ViewHolder {
        final TextView tvName;
        final TextView tvArtist;
        final TextView tvSummary;
        final ImageView tvImage;

        ViewHolder(View v) {
            tvName = v.findViewById(R.id.tvName);
            tvArtist = v.findViewById(R.id.tvArtist);
            tvSummary = v.findViewById(R.id.tvSummary);
            tvImage = (ImageView) v.findViewById(R.id.tvImage);
        }
    }
}
