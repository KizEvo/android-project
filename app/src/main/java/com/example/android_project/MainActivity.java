package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

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
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                Bundle bundle = new Bundle();

                EditText nameInput = (EditText) findViewById(R.id.editTextText1);
                EditText idInput = (EditText) findViewById(R.id.editTextText2);
                EditText classInput = (EditText) findViewById(R.id.editTextText3);
                EditText phoneInput = (EditText) findViewById(R.id.editTextNumberDecimal);

                CheckBox year4Box = (CheckBox) findViewById(R.id.checkBox4);
                CheckBox year3Box = (CheckBox) findViewById(R.id.checkBox2);
                CheckBox year2Box = (CheckBox) findViewById(R.id.checkBox3);
                CheckBox year1Box = (CheckBox) findViewById(R.id.checkBox);

                CheckBox mthtnBox = (CheckBox) findViewById(R.id.checkBox19);
                CheckBox dtBox = (CheckBox) findViewById(R.id.checkBox21);
                CheckBox vtBox = (CheckBox) findViewById(R.id.checkBox18);
                CheckBox naBox = (CheckBox) findViewById(R.id.checkBox20);

                EditText planInput = (EditText) findViewById(R.id.editTextTextMultiLine2);

                try {
                    bundle.putString("nameInput", nameInput.getText().toString());
                    bundle.putString("idInput", idInput.getText().toString());
                    bundle.putString("classInput", classInput.getText().toString());
                    bundle.putString("phoneInput", phoneInput.getText().toString());

                    int year = 0;
                    if (year4Box.isChecked()) {
                        year = 4;
                    } else if (year3Box.isChecked()) {
                        year = 3;
                    } else if (year2Box.isChecked()) {
                        year = 2;
                    } else if (year1Box.isChecked()) {
                        year = 1;
                    }
                    bundle.putInt("yearBox", year);

                    String specialized = "";
                    if (mthtnBox.isChecked()) {
                        specialized += "MT-HTN";
                    } else if (dtBox.isChecked()) {
                        specialized += "DT";
                    } else if (vtBox.isChecked()) {
                        specialized += "DT";
                    } else if (naBox.isChecked()) {
                        specialized += "N/A";
                    }
                    bundle.putString("specializedInput", specialized);
                    bundle.putString("planInput", planInput.getText().toString());

                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.d("MyApp", Objects.requireNonNull(e.getMessage()));
                }
            }
        });
    }
}