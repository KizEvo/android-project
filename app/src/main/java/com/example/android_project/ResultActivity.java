package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Get pointer to Result id
        TextView result = (TextView) findViewById(R.id.res_text1);
        // Get the inputs from MainActivity bundle
        Bundle bundle = getIntent().getExtras();
        // Calculate the equation
        if (bundle != null) {
            QuadraticEquation fx = new QuadraticEquation(
                    bundle.getFloat("inputA"),
                    bundle.getFloat("inputB"),
                    bundle.getFloat("inputC")
            );
            String [] res = fx.Solve();
            String output = "";
            switch (res[0]) {
                case "LESS":
                    output += "Equation has 2 distinct roots";
                    break;
                case "EQUAL":
                    output += "Equation has 1 distinct root";
                    break;
                case "LARGER":
                    output += "Equation has no real roots";
                    break;
            }
            output += "\nx0: " + res[1] + "\n";
            output += "x1: " + res[2];
            // Display the result
            result.setText(output);
        } else {
            // Error case
            String output = "Inputs cannot be empty";
            result.setText(output);
        }
        Button btn = (Button) findViewById(R.id.res_button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
