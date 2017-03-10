package com.example.amantaeva.begimai.converter;

import android.content.Context;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

public class CurrencyActivity extends AppCompatActivity {

    public static final String CURRENCY_RATIOS_JSON = "currency_ratios.json";

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
        setContentView(R.layout.activity_currency);

        firstCurrencySpinner =(Spinner)findViewById(R.id.firstCurrencySpinner);
        secondCurrencySpinner =(Spinner)findViewById(R.id.secondCurrencySpinner);
        firstCurrencyEditText = (EditText)findViewById(R.id.firstCurrencyEditText);
        secondCurrencyEditText = (EditText)findViewById(R.id.secondCurrencyEditText);
        customConversionRatioEditText = (EditText)findViewById(R.id.customConversionRatioEditText);

        populateSpinner();
        setupListeners();
    }

    
    private void populateSpinner() {
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this, R.array.currencies,
                        android.R.layout.simple_spinner_item
                );
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        firstCurrencySpinner.setAdapter(adapter);
        secondCurrencySpinner.setAdapter(adapter);
    }

    private  void setupListeners() {
        setupSpinnerListeners();

    }

    private void setupSpinnerListeners() {
        AdapterView.OnItemSelectedListener onItemSelectedListener =
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //have to do calculations
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                };
        firstCurrencySpinner.setOnItemSelectedListener(onItemSelectedListener);
        secondCurrencySpinner.setOnItemSelectedListener(onItemSelectedListener);
    }

}
