package com.example.android_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String currentNumber = "";
    private double result = 0;
    private String lastOperation = "";
    private boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        display = findViewById(R.id.display);
        // Number buttons
        setNumberButtonListener(R.id.button_0, "0");
        setNumberButtonListener(R.id.button_1, "1");
        setNumberButtonListener(R.id.button_2, "2");
        setNumberButtonListener(R.id.button_3, "3");
        setNumberButtonListener(R.id.button_4, "4");
        setNumberButtonListener(R.id.button_5, "5");
        setNumberButtonListener(R.id.button_6, "6");
        setNumberButtonListener(R.id.button_7, "7");
        setNumberButtonListener(R.id.button_8, "8");
        setNumberButtonListener(R.id.button_9, "9");

        // Operation buttons
        setOperationButtonListener(R.id.button_plus, "+");
        setOperationButtonListener(R.id.button_minus, "-");
        setOperationButtonListener(R.id.button_multiply, "×");
        setOperationButtonListener(R.id.button_divide, "/");

        // Equals button
        Button buttonEquals = findViewById(R.id.button_equals);
        buttonEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentNumber.isEmpty() && !lastOperation.isEmpty()) {
                    double secondNumber = Double.parseDouble(currentNumber);
                    switch (lastOperation) {
                        case "+":
                            result += secondNumber;
                            break;
                        case "-":
                            result -= secondNumber;
                            break;
                        case "×":
                            result *= secondNumber;
                            break;
                        case "/":
                            if (secondNumber != 0) {
                                result /= secondNumber;
                            } else {
                                display.setText("Error");
                                return;
                            }
                            break;
                    }
                    display.setText(String.valueOf(result));
                    currentNumber = "";
                    lastOperation = "";
                    isNewOperation = true;
                }
            }
        });

        // Delete button
        Button buttonDel = findViewById(R.id.button_del);
        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNumber = "";
                result = 0;
                lastOperation = "";
                isNewOperation = true;
                display.setText("0");
            }
        });

        // Percentage button
        Button buttonPercent = findViewById(R.id.button_percent);
        buttonPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentNumber.isEmpty()) {
                    double number = Double.parseDouble(currentNumber);
                    number = number / 100;
                    currentNumber = String.valueOf(number);
                    display.setText(currentNumber);
                }
            }
        });
    }

    private void setNumberButtonListener(int buttonId, final String number) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNewOperation) {
                    currentNumber = "";
                    isNewOperation = false;
                }
                currentNumber += number;
                display.setText(currentNumber);
            }
        });
    }

    private void setOperationButtonListener(int buttonId, final String operation) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentNumber.isEmpty()) {
                    // If there's a number entered, process it
                    if (lastOperation.isEmpty()) {
                        // First operation: set result to current number
                        result = Double.parseDouble(currentNumber);
                    } else {
                        // Perform the previous operation
                        double secondNumber = Double.parseDouble(currentNumber);
                        switch (lastOperation) {
                            case "+":
                                result += secondNumber;
                                break;
                            case "-":
                                result -= secondNumber;
                                break;
                            case "×":
                                result *= secondNumber;
                                break;
                            case "/":
                                if (secondNumber != 0) {
                                    result /= secondNumber;
                                } else {
                                    display.setText("Error");
                                    return;
                                }
                                break;
                        }
                        display.setText(String.valueOf(result));
                    }
                    lastOperation = operation;
                    currentNumber = "";
                    isNewOperation = true;
                } else if (isNewOperation && result != 0) {
                    // After equals, use the current result for the next operation
                    lastOperation = operation;
                    isNewOperation = true;
                }
            }
        });
    }
}