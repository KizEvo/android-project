package com.example.android_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SubActivity extends AppCompatActivity {

    String tempStr = "";
    SpannableStringBuilder spannable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Link object by component ID
        EditText editField = findViewById(R.id.editField);
        Button returnWork = findViewById(R.id.returnWork);
        //Get bundle
        Bundle bundle = getIntent().getBundleExtra("MyData1");

        //Get data and display
        if(bundle != null){
            tempStr = bundle.getString("student");
            editField.setText(tempStr);
        }

        //Monitor changes in EditText and apply textStyle on EditText
        editField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editField.removeTextChangedListener(this);
                spannable = new SpannableStringBuilder(s);
                spannable.setSpan(new ForegroundColorSpan(Color.RED), start, start + count, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                editField.setText(spannable);
                editField.setSelection(start+count);
                editField.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //Button click event
        returnWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize intent and bundle
                Intent resultIntent = new Intent();
                Bundle resultBundle = new Bundle();
                //Get data with exception check
                try {
                    String teacherPara = editField.getText().toString();
                    // Store the inputs to bundle
                    resultBundle.putString("teacher", teacherPara);
                    resultIntent.putExtra("MyData2", resultBundle);
                    setResult(88, resultIntent);
                    finish();
                } catch (Exception e) {
                    Log.d("MyApp", "User provided missing inputs");
                }
            }
        });

    }
}