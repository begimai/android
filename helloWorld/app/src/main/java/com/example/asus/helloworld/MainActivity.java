package com.example.asus.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText;
    private TextView messageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // when activity main is created

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        messageEditText = (TextView) findViewById(R.id.messageEditText);
    }

    public void great(View view) {
        String name = nameEditText.getText().toString();
        messageEditText.setText(getString(R.string.greetingMessage, name));
    }
}
