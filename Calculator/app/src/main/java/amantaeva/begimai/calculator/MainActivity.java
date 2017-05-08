package amantaeva.begimai.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText resultEditText;

    private Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultEditText = (EditText) findViewById(R.id.resultEditText);
        calculator = new Calculator();
    }

    public void onClearButtonClick(View view) {
        calculator.clear();
        displayResult();
    }

    public void onNegateButtonClick(View view) {
        calculator.negate();
        displayResult();
    }

    public void onBinaryOperationButtonClick(View view) {
        String tag = view.getTag().toString();
        Calculator.Operation operation = Calculator.Operation.valueOf(tag);

        try {
            calculator.performBinaryOperation(operation);
        } catch (ArithmeticException e) {
            Log.d("Main Activity", e.getMessage());
            reportError();
        }

        displayResult();
    }

    public void onAddDigitButtonClick(View view) {
        TextView textView = (TextView) view;
        int digit = Integer.parseInt(textView.getText().toString());
        calculator.addDigit(digit);
        displayResult();
    }

    public void onResultButtonClick(View view) {
        calculator.calculateResult();
        displayResult();
    }

    private void displayResult() {
        resultEditText.setText(String.valueOf(calculator.getResult()));
    }

    private void reportError() {
        Toast.makeText(this, "Error: can't divide by zero", Toast.LENGTH_SHORT).show();
    }
}
