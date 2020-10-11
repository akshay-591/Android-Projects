//Program to make an app which shows rss feeds
//rss feeds are in form of XML file
/*so what we gonna do here is first connect to the website with the help of URL and HttpConnection
class and then read that data with with the help of InputStreamReader attaching it with BufferReader
then to show only usefull information we parse it using XML parsing class of java */
/* the data (XMl file) will be downloaded in background(Worker Thread) using AsyncTask class because it is advisable
to not to do intensive work in UI Thread (main Thread) see the notes Process and Thread Chapter for more Information */
package kumar.akshay.datadownloader;

//importing usefull classes

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


// main activity
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main Activity";
    private ListView listapp;

    //overriding onCreate() method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //calling super method and passing current state
        super.onCreate(savedInstanceState);
        //attaching layout
        setContentView(R.layout.activity_main);
        listapp = (ListView) findViewById(R.id.xmlListView);
        //link to rss feed
        String Url = LauncherActivity.URL;
        //displaying in logcat
        Log.d(TAG, "onCreate : Starting Asynch");
        // creating DataDownloader class reference
        DataDownlaoder dd = new DataDownlaoder();
        //calling execute() method of AsychTask class
        dd.execute(Url);
        //displaying in logcat
        Log.d(TAG, "onCreate: Done");
    }

    //creating sub class of AsyncTask class and passing parameter
    private class DataDownlaoder extends AsyncTask<String, Void, String> {

        private TextView textView;
        private static final String TAG = "Downloading";

        // overriding doInBackground and receving website Url as parameter method of Super class
        //in this method we execute the task which we do not want to do in main or UI thread
        // so in here we will connect to website and download the XML file
        @Override
        protected String doInBackground(String... strings) {
            //displaying in logcat
            Log.d(TAG, "doInBackground: start");
            //calling operation() method and passing the Url
            //operation() method also going to be executed in work thread since we are calling it form doInBackground method
            // we will receive the XML file as string in rssfeed reference variable
            String rssfeed = operation(strings[0]);
            //if the return data is null
            //then there must be an error ex- either the link is wrong or Internet problem

            if (rssfeed == null) {
                Log.e(TAG, "doInBackground: downloading error");
            }
            // returning or passing rssfeed (Downloaded XML file) data which will be pass on
            // to the onPostExecute() method
            return rssfeed;
        }


        //method to perform connecting and downloading
        // called by doInBackground() method
        // this method will also going to work on work thread
        private String operation(String URl) {
            // creating StringBuilder reference
            StringBuilder data = new StringBuilder();
            //initializing try block
            try {
                //creating object reference of URL class and passing URL to it of XML file
                URL obj = new URL(URl);
                // creating object reference to HttpConnection class and opening the Connection to the website
                HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
                // checking if connection made successfully or not 200 will be the Response code for connecting
                int response = conn.getResponseCode();
                //displaying that code in logcat to check
                Log.d(TAG, "operation: response code" + response);
                // now connecting stream to data
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));


             /* tutorial source code
             int charsRead;
             char[] inputBuffer=new char[500];
                while(true)
                {
                    charsRead =br.read(inputBuffer);
                    if(charsRead<0){
                        break;
                    }
                    if(charsRead>0){
                        data.append(String.copyValueOf(inputBuffer,0,charsRead));
                    }
                }*/
                //my code
                //using readLine () of BufferedReader class we read the data and store it as a String in data variable

                //we will store line by line data in 'line" variable then this line will get appended in StringBuilder variable "data"
                String line;
                while ((line = br.readLine()) != null) {
                    data.append(line);
                    data.append("\n");
                }

                // closing connection
                br.close();

            }
            //initializing catch block to catch Exception if any
            catch (Exception ref) {
                Log.e(TAG, "operation: error " + ref.getMessage());

            }
            // returning data as a String back to doInBackground() method for further process
            return data.toString();
        }


        // Overriding onPostExecute() Method of super class AsyncTask
        //this method will get executed in main thread
        //we will use this thread to do display data on textView which
        @Override
        protected void onPostExecute(String result) {
            //calling super method passing result
            super.onPostExecute(result);
            /*creating reference to ParseApplication class which we have created to parse the XML data
             means to extract the usefull data */
            ParseApplications parseApplications = new ParseApplications();
            //calling parse method of parseApplication class and passing the XML data (result) to it
            if (parseApplications.parse(result))
            {
                FeedAdapter feedAdapter = new FeedAdapter(MainActivity.this, R.layout.list_record, parseApplications.getApplication());
                listapp.setAdapter(feedAdapter);
            }
            /*ArrayAdapter<FeedEntry> arrayAdapter = new ArrayAdapter<>(MainActivity.this,R.layout.list_item, parseApplications.getApplication());
            listapp.setAdapter(arrayAdapter);*/
          /*  attaching textView widget to textView reference of TextView class
            textView=(TextView)findViewById(R.id.textView);
            enabling scrolling movement
            textView.setMovementMethod(new ScrollingMovementMethod());



            //now creating an ArrayList collection class reference
            ArrayList<FeedEntry> application= new ArrayList<>();

            // calling getApplication method of ParseApplication class
            // which will return collection class object reference which get store or attach it in "application"(ArrayList object) reference
            application=parseApplications.getApplication();
            // now we will take the data from collection object and store that data in String so that we can show it in text view
            String line="";
            //using for each loop we will read and Store the data in string one by one
            for ( Object i:application)
            {
                //here "i" represent the element of ArrayList

                line+=" \n "+i.toString();//calling toString() method of feedEntry class which will return the data instead of hashnumber
            }

            //displaying the data in textView
            textView.setText("TOP TEN APPS ACCORDING TO APPLE RSS FEED \n"+line);*/


        }


    }
}

