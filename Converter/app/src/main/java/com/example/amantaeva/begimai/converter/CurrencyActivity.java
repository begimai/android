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

    // declaration of EditTexts, Spinners, TextWatcher and JsonObject
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

        // assigning them by ID
        firstCurrencySpinner =(Spinner)findViewById(R.id.firstCurrencySpinner);
        secondCurrencySpinner =(Spinner)findViewById(R.id.secondCurrencySpinner);
        firstCurrencyEditText = (EditText)findViewById(R.id.firstCurrencyEditText);
        secondCurrencyEditText = (EditText)findViewById(R.id.secondCurrencyEditText);
        customConversionRatioEditText = (EditText)findViewById(R.id.customConversionRatioEditText);

        populateSpinner();
        setupListeners();
        loadConversionRatios();
    }

    // Adapters help to tell spinners how to show up, what to list inside
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

    // Listeners are used to collect data that have been done
    private  void setupListeners() {
        setupSpinnerListeners();
        setupInputFieldsListeners();

    }

    // spinner's item might change, so we have to recalculate
    private void setupSpinnerListeners() {
        AdapterView.OnItemSelectedListener onItemSelectedListener =
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        calculate();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                };
        firstCurrencySpinner.setOnItemSelectedListener(onItemSelectedListener);
        secondCurrencySpinner.setOnItemSelectedListener(onItemSelectedListener);
    }

    // gets entered numbers from EditText and CustomRatio field
    private void setupInputFieldsListeners() {
        firstCurrencyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }
        });

        customConversionRatioEditTextTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                double value = 0.0;
                try {
                    value = Double.parseDouble(s.toString());
                } catch (NumberFormatException ignored) {
                    return;
                }

                String firstSelectedUnit = firstCurrencySpinner.getSelectedItem().toString();
                String secondSelectedUnit = secondCurrencySpinner.getSelectedItem().toString();

                String currencyPair = firstSelectedUnit + " - " + secondSelectedUnit;

                try {
                    conversionRatios.put(currencyPair, value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        customConversionRatioEditText.addTextChangedListener(customConversionRatioEditTextTextWatcher);
    }

    // is calculate or recalculate values
    private  void calculate() {
        double value;
        try {
            value = Double.parseDouble(firstCurrencyEditText.getText().toString());
        } catch (NumberFormatException ignored) {
            return;
        }

        double ratio = getConversionRatio();
        double result = value * ratio;

        secondCurrencyEditText.setText(
                String.format(
                        Locale.getDefault(), "%.2f", result
                )
        );
    }

    // gets a ratio from json for a specific currencies
    private double getConversionRatio() {
        String firstSelectedUnit = firstCurrencySpinner.getSelectedItem().toString();

        String secondSelectedUnit = secondCurrencySpinner.getSelectedItem().toString();

        String currencyPair = firstSelectedUnit + " - " + secondSelectedUnit;

        return conversionRatios.optDouble(
                currencyPair, 1.0f
        );
    }

    // this loads our json file, so we can use it
    private void loadConversionRatios() {
        InputStream inputStream = null;
        File conversionRatiosFile;
        if ((conversionRatiosFile = getFileStreamPath(CURRENCY_RATIOS_JSON)).exists()) {
            try {
                inputStream = new FileInputStream(conversionRatiosFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            inputStream = getResources().openRawResource(R.raw.currency_ratios);
        }

        String jsonFileContent = "";

        if(inputStream != null) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder stringBuilder = new StringBuilder();

                String line;
                while((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                jsonFileContent = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            conversionRatios = new JSONObject(jsonFileContent);
        } catch (JSONException e) {
            e.printStackTrace();
            conversionRatios = new JSONObject();
            setConversionRatioText(getConversionRatio());
        }
    }

    // fills a field with ratio that can be changed later by users
    private  void setConversionRatioText(double ratio) {
        customConversionRatioEditText.removeTextChangedListener(customConversionRatioEditTextTextWatcher);
        customConversionRatioEditText.setText(String.valueOf(ratio));
        customConversionRatioEditText.addTextChangedListener(customConversionRatioEditTextTextWatcher);
    }

}
