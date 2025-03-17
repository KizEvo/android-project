package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

        Button btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                Bundle bundle = new Bundle();

                EditText inputA = (EditText) findViewById(R.id.editTextNumberSigned1);
                EditText inputB = (EditText) findViewById(R.id.editTextNumberSigned2);
                EditText inputC = (EditText) findViewById(R.id.editTextNumberSigned3);
                try {
                    float inputANumb = Float.parseFloat(inputA.getText().toString());
                    float inputBNumb = Float.parseFloat(inputB.getText().toString());
                    float inputCNumb = Float.parseFloat(inputC.getText().toString());
                    // Store the inputs to bundle
                    bundle.putFloat("inputA", inputANumb); bundle.putFloat("inputB", inputBNumb); bundle.putFloat("inputC", inputCNumb);
                    intent.putExtras(bundle);
                } catch (Exception e) {
                    Log.d("MyApp", "User provided missing inputs");
                }

                startActivity(intent);
            }
        });
    }
}
