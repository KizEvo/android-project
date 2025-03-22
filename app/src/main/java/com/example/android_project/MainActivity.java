package com.example.android_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String teacherPara = "", studentPara = "";
    SpannableStringBuilder spannable;

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

        //Link object by component ID
        EditText studentWork = findViewById(R.id.studentWork);
        Button submitButton = findViewById(R.id.submitButton);

        //Button click event
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize intent and bundle
                Intent transIntent = new Intent(MainActivity.this, SubActivity.class);
                Bundle transBundle = new Bundle();
                //Get data with exception check
                try {
                    studentPara = studentWork.getText().toString();
                    // Store the inputs to bundle
                    transBundle.putString("student", studentPara);
                    transIntent.putExtra("MyData1", transBundle);
                } catch (Exception e) {
                    Log.d("MyApp", "User provided missing inputs");
                }

                //Create request with code 99
                startActivityForResult(transIntent, 99);
            }
        });
    }

    protected ArrayList<String> splittingWord(String str) {
        char arr[] = str.toCharArray();
        String word = "";
        ArrayList<String> strList = new ArrayList<>();

        int j=0;
        for(int i=0; i< arr.length; i++) {
            if(arr[i] != ' ' && arr[i] != '\0') {
                word += arr[i];
            } else {
                strList.add(word);
                word = "";
            }
        }
        strList.add(word);

        return strList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == 88) {
            //Get bundle from request replying
            Bundle receivedBundle = data.getBundleExtra("MyData2");
            //Get data and display
            if(receivedBundle != null) {
                teacherPara = receivedBundle.getString("teacher");
                spannable = new SpannableStringBuilder(teacherPara);
                if(teacherPara != null) {
                    ArrayList<String> goldenWord = splittingWord(teacherPara);
                    ArrayList<String> actualWord = splittingWord(studentPara);
                    if(goldenWord.size() == actualWord.size()){
                        for(int i=0; i < goldenWord.size(); i++){
                            int start_index = teacherPara.indexOf(goldenWord.get(i));
                            int end_index = start_index + goldenWord.get(i).length();
                            if( goldenWord.get(i).equals(actualWord.get(i)) ) {
                                spannable.setSpan(new ForegroundColorSpan(Color.GREEN), start_index, end_index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            } else {
                                spannable.setSpan(new ForegroundColorSpan(Color.RED), start_index, end_index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }
                    }
                }

                EditText teacherWork = findViewById(R.id.teacherWork);
                teacherWork.setText(spannable);
            }
        }
    }

}