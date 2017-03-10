package com.gmail.amantaeva.begimai;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.amantaeva.begimai.converter.R;

import org.json.JSONObject;

public class CurrencyActivity extends AppCompatActivity {
    private EditText firstCurrencyEditText;
    private EditText secondCurrencyEditText;
    private EditText customConversionRatioEditText;
    private TextWatcher customConversionRatioEditTextTextWatcher;

    private Spinner firstCurrencySpinner;
    private Spinner secondCurrencySpinner;

    private JSONObject conversionRatios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //json
}
