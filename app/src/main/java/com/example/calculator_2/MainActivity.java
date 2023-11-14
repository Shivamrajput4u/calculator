package com.example.calculator_2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private TextView textResult;
    private String currentInput = "";
    private double result = 0;
    private String operator = "";
    private boolean clearInput = false;
    private Stack<String> memoryStack = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = findViewById(R.id.textResult);
    }

    public void onNumberClick(View view) {
        if (clearInput) {
            currentInput = "";
            clearInput = false;
        }

        Button button = (Button) view;
        currentInput += button.getText().toString();
        textResult.setText(currentInput);
    }

    public void onOperatorClick(View view) {
        if (currentInput.isEmpty()) {
            return;
        }

        Button button = (Button) view;
        String newOperator = button.getText().toString();

        if (!operator.isEmpty() && !currentInput.equals("-")) {
            performCalculation();
            DecimalFormat decimalFormat = new DecimalFormat("#.##########");
            String formattedResult = decimalFormat.format(result);
            textResult.setText(formattedResult);
        } else {
            result = Double.parseDouble(currentInput);
        }

        operator = newOperator;
        clearInput = true;
    }

    public void onEqualClick(View view) {
        if (operator.isEmpty()) {
            return;
        }

        if (!currentInput.isEmpty()) {
            performCalculation();
            DecimalFormat decimalFormat = new DecimalFormat("#.##########");
            String formattedResult = decimalFormat.format(result);
            textResult.setText(formattedResult);

            currentInput = formattedResult;
            operator = "";
        }
    }

    public void onDecimalClick(View view) {
        if (clearInput) {
            currentInput = "0.";
            clearInput = false;
        } else if (!currentInput.contains(".")) {
            currentInput += ".";
        }
        textResult.setText(currentInput);
    }

    public void onClearClick(View view) {
        currentInput = "";
        result = 0;
        operator = "";
        textResult.setText("0");
    }

    public void onPlusMinusClick(View view) {
        if (!currentInput.isEmpty() && !currentInput.equals("0")) {
            if (currentInput.charAt(0) == '-') {
                currentInput = currentInput.substring(1);
            } else {
                currentInput = "-" + currentInput;
            }
            textResult.setText(currentInput);
        }
    }

    public void onPercentClick(View view) {
        if (!currentInput.isEmpty()) {
            double percentValue = Double.parseDouble(currentInput) / 100.0;
            currentInput = String.valueOf(percentValue);
            textResult.setText(currentInput);
        }
    }

    public void onMemoryClick(View view) {
        Button button = (Button) view;
        String memoryAction = button.getText().toString();

        switch (memoryAction) {
            case "MC":
                memoryStack.clear();
                break;
            case "MR":
                if (!memoryStack.isEmpty()) {
                    currentInput = memoryStack.peek();
                    textResult.setText(currentInput);
                }
                break;
            case "M+":
                memoryStack.push(currentInput);
                break;
            case "M-":
                if (!memoryStack.isEmpty()) {
                    memoryStack.pop();
                }
                break;
        }
    }

    public void onSqrtClick(View view) {
        if (!currentInput.isEmpty() && !currentInput.equals("-")) {
            double sqrtValue = Math.sqrt(Double.parseDouble(currentInput));
            currentInput = String.valueOf(sqrtValue);
            textResult.setText(currentInput);
        }
    }

    public void onBackspaceClick(View view) {
        if (!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            if (currentInput.isEmpty()) {
                currentInput = "0";
            }
            textResult.setText(currentInput);
        }
    }

    private void performCalculation() {
        double secondNumber = Double.parseDouble(currentInput);

        switch (operator) {
            case "+":
                result += secondNumber;
                break;
            case "-":
                result -= secondNumber;
                break;
            case "*":
                result *= secondNumber;
                break;
            case "/":
                if (secondNumber != 0) {
                    result /= secondNumber;
                } else {
                    textResult.setText("Error");
                    result = 0;
                    operator = "";
                    currentInput = "";
                    clearInput = true;
                    return;
                }
                break;
            case "^":
                result = Math.pow(result, secondNumber);
                break;
            default:
                break;
        }

        currentInput="";
}
}