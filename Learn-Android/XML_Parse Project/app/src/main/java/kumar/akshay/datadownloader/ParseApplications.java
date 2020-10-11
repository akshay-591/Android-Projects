//this class will be used to parse or we can say extract the usefull data from XML file

package kumar.akshay.datadownloader;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class ParseApplications {
    private static final String TAG = "ParseApplications";

    // initializing collection class ArrayList object reference setting parameter as feedEntry so that FeedEntry class object will get Store in this collection class
    private ArrayList<FeedEntry> application;

    //initializing constructor
    ParseApplications()

    {
        //creating object reference
        this.application=new ArrayList<>();
    }

//creating getter method for this class which can be used to return the object reference of the ArrayList collection
    public ArrayList<FeedEntry> getApplication() {
        return application;
    }
    // creating parse () method which receive Xml data and parse it
    public boolean parse(String xmlData)
    {
        Log.d(TAG, "parse: in");
        boolean status=true;
        FeedEntry currentRecord=null;
        boolean inEntry=false;
        String textValue="";

        //initializing try block
        try {
            //ussing XMLParse class to parse the data
            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            //getting Event type from Xml data
            int eventType= xpp.getEventType();

            //initializing while loop
            //if the event type is END_DOCUMENT loop will get terminated
            while(eventType!=XmlPullParser.END_DOCUMENT)
            {
                //getting tag name (ex- <entry>) from XmL file
                String tagName=xpp.getName();
                // depending on the Eventype Switch will
                switch(eventType)
                {
                    //if eventType is '1' ( means StartTag ex- <entry>) this case will get executed
                    case XmlPullParser.START_TAG:
                        Log.d(TAG, "parse: Starting Tag for "+tagName);

                        if ("entry".equalsIgnoreCase(tagName))
                        {
                            //if tagname is "entry" this statements gets executed
                            //inEntry become true
                            inEntry=true;
                            //FeedEntry class objects gets Created
                            currentRecord=new FeedEntry();
                        }
                        //if tagname is not "entry" nothing will happen
                        //after any of the condition this switch loop will break
                        break;

                        //if eventType is "2" ( means Text ex- <>abcd</> here abcd is text) this case will gets executed
                    case XmlPullParser.TEXT:
                        // text will get stored in textValue
                        textValue= xpp.getText();
                        //after this switch will break
                        break;

                        // if eventType is "3" (means EndTag ex- </entry>) this case will gets executed
                    case XmlPullParser.END_TAG:
                            Log.d(TAG, "parse: Ending tag for "+tagName);
                            /* if inEntry is true means Start tag was <entry> then these next statement will
                                gets executed otherwise this case will break without doing anything*/
                            if(inEntry)
                            {
                                //after the EndTag </entry> detected inEntry become false again
                                // but between the Start and end Tag of entry there are others tag too which wee need
                                if("entry".equalsIgnoreCase(tagName)) {
                                    application.add(currentRecord);
                                    inEntry = false;
                                }
                                //if tagname(EndTag) is "</name>" its text which already gets stored in
                                // Textvalue gets store in FeedEntry variable using setName() method
                                else if ("name".equalsIgnoreCase(tagName)) {
                                    currentRecord.setName(textValue);
                                }
                                //if tagname(EndTag) is "</artist>" its text which already gets stored in
                                // Textvalue gets store in FeedEntry variable using setArtist() method
                                else if("artist".equalsIgnoreCase(tagName)) {
                                    currentRecord.setArtist(textValue);
                                }
                                //if tagname(EndTag) is "</release>" its text which already gets stored in
                                // Textvalue gets store in FeedEntry variable using setReleaseDate() method
                                else if ("release".equalsIgnoreCase(tagName)) {
                                    currentRecord.setReleaseDate(tagName);
                                }
                                //if tagname(EndTag) is "</summary>" its text which already gets stored in
                                // Textvalue gets store in FeedEntry variable using setSummary() method
                                else if("summary".equalsIgnoreCase(tagName)){
                                    currentRecord.setSummary(textValue);
                                }
                                //if tagname(EndTag) is "</image>" its text which already gets stored in
                                // Textvalue gets store in FeedEntry variable using setImageURL() method
                                else if ("image".equalsIgnoreCase(tagName)){
                                    currentRecord.setImageURL(textValue);
                                }
                            }
                            break;
                            //default case is nothing
                    default:
                        //nothing else to do
                }
                //using next() method eventType gets to the next EventType
                eventType=xpp.next();
            }
           for(FeedEntry app: application)
            {
                Log.d(TAG, "**************");
                Log.d(TAG, app.toString());
            }

        }
        //initializing catch block which will catch the exception if any
        catch (Exception ref)
        {
            status=false;
            ref.printStackTrace();
        }
        Log.d(TAG, "parse: out");
        // this method will return the status as True or False if false means there may be some error in the data and exception gets triggered
        return status;


    }
}
