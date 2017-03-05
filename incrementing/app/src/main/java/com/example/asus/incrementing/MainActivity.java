package com.example.asus.incrementing;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    private TextView messageEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // when activity main is created, open, analyze

    }
    public void increaseCount(View view) {
        messageEditText = (TextView) findViewById(R.id.messageEditText);
        int num = Integer.parseInt(messageEditText.getText().toString()) + 1;
        String message = String.format(Locale.getDefault(), "%04d", num);
        messageEditText.setText(message);
    }
    public void decreaseCount(View view){
        messageEditText = (TextView) findViewById(R.id.messageEditText);
        int num = Integer.parseInt(messageEditText.getText().toString()) - 1;
        if(num >= 0) {
            String message = String.format(Locale.getDefault(), "%04d", num);
            messageEditText.setText(message);
        }
        else {
            Toast.makeText(getApplicationContext(), "Number cannot be negative", Toast.LENGTH_SHORT).show();
        }
    }
}
