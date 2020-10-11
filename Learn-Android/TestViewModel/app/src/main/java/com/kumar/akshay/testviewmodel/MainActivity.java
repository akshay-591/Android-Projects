package com.kumar.akshay.testviewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kumar.akshay.testviewmodel.databinding.ActivityMainBinding;

    public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ViewModelProvider.Factory factory = new ViewModelProvider.NewInstanceFactory();
        MyViewModel model = new ViewModelProvider(this,factory).get(MyViewModel.class);
        binding.textView.setText(model.getData());

        //onclick using lambda expression of java 8
        binding.button.setOnClickListener(v-> {
              model.setData(binding.editText.getText().toString());
                binding.textView.setText(model.getData());
        });
        Log.d(TAG, "onCreate: ends");
    }

}
