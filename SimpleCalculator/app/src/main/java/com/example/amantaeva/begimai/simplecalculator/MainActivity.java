package com.example.amantaeva.begimai.simplecalculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText firstInput;
    private EditText secondInput;

    RadioGroup operationRadioGroup;

    private TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Log.e("error", "error message");

        firstInput = (EditText)findViewById(R.id.editText1);
        secondInput = (EditText)findViewById(R.id.editText2);

        TextWatcher textwatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }
        };

        operationRadioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        operationRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                calculate();
            }
        });

        firstInput.addTextChangedListener(textwatcher);
        secondInput.addTextChangedListener(textwatcher);

        resultView = (TextView)findViewById(R.id.result);
    }

    private void calculate() {
        double firstOperand = 0;
        try {
            firstOperand = Double.parseDouble(firstInput.getText().toString());
        }
        catch (NumberFormatException e)
        {
            resultView.setText("");
            return;
        }
        double secondOperand = 0;
        try {
            secondOperand = Double.parseDouble(secondInput.getText().toString());
        }
        catch(NumberFormatException e)
        {
            resultView.setText("");
            return;
        }
        double result = 0;

        int selectedOperation = operationRadioGroup.getCheckedRadioButtonId();
        switch (selectedOperation){
            case R.id.radioButtonAdd:
                result = firstOperand + secondOperand;
                break;
            case R.id.radioButtonDivide:
                if(secondOperand == 0){
                    Context context = getApplicationContext();
                    CharSequence text = "Cannot divide by zero";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {
                    result = firstOperand / secondOperand;
                }
                break;
            case R.id.radioButtonMultiply:
                result = firstOperand * secondOperand;
                break;
            case R.id.radioButtonSubtract:
                result = firstOperand - secondOperand;
                break;
        }

        resultView.setText(
                String.format(Locale.getDefault(), "%.2f", result)
        );
    }
}