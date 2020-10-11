package kumar.akshay.challenge1;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity ;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";
    private static final String TEXT_CONTENT = "SavedState";

    /*Declaring instance widget variable */
    private Button button;
    private EditText userInput;
    private TextView textView;
    String result;
    private Double value=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate:in");
        super.onCreate(savedInstanceState);
        //attaching layout
        setContentView(R.layout.activity_main);
        /* attaching widget "variable" to widget "Id" using findViewById() method of "View" class */
        userInput = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        //for enabling scrolling in text view
        textView.setMovementMethod(new ScrollingMovementMethod());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = userInput.getText().toString();
                textView.append(result);
                userInput.setText("");
            }
        });
        Log.d(TAG, "onCreate:out");
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart:in");
        super.onStart();
        Log.d(TAG, "onStart:out");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume:in");
        super.onResume();
        Log.d(TAG, "onResume:out");
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause:in");
        super.onPause();
        Log.d(TAG, "onPause:out");
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop:in");
        super.onStop();
        Log.d(TAG, "onStop:out");
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart:in");
        super.onRestart();
        Log.d(TAG, "onRestart:out");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState:in");
        super.onRestoreInstanceState(savedInstanceState);
        //restoring the textView component data on rotation of the Screen.


        textView.setText(savedInstanceState.getString(TEXT_CONTENT));
        Log.d(TAG, "onRestoreInstanceState:out");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSavedInstanceState:in");
        //to save the textview component data on rotation of the screen
        //we first store that data in TEXT_CONTENT variable using putString() method
        //because onSaveInstanceState () method does not save non editable component data like text
        //view data automaticaly
        outState.putString(TEXT_CONTENT, textView.getText().toString());
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSavedInstanceState:out");
    }


    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy:in");
        super.onDestroy();
        Log.d(TAG, "onDestroy:out");
    }
}
