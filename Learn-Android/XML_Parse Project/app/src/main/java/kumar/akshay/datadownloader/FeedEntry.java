// this class will be store the extracted usefull data from XML file

package kumar.akshay.datadownloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class FeedEntry {
    //initializing variable
    private static final String TAG = "FeedEntry";
    private String name;
    private String artist;
    private String releaseDate;
    private String summary;
    private String imageURL;
    private Bitmap im;

    //initializing getter and setter methods for these variable

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImage() {
        return imageURL;


    }


    public void setImageURL(String imageURL) {

        this.imageURL = imageURL;
    }

    //overriding toString method
    @Override
    public String toString() {
        return "\n" +
                "Name=" + name + "\n" +
                "Artist=" + artist + "\n" +
                "ReleaseDate=" + releaseDate + "\n" +
                "Summary=" + summary + "\n" +
                "ImageURL=" + imageURL + "\n";

    }


}
