package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

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

        EditText nameET = findViewById(R.id.nameET);
        EditText idET = findViewById(R.id.idET);
        EditText classET = findViewById(R.id.classET);
        EditText phoneNumET = findViewById(R.id.phoneNumET);
        EditText planET = findViewById(R.id.planET);

        RadioButton opt1 = findViewById(R.id.firstYearRB);
        RadioButton opt2 = findViewById(R.id.secondYearRB);
        RadioButton opt3 = findViewById(R.id.thirdYearRB);
        RadioButton opt4 = findViewById(R.id.fourthYearRB);

        CheckBox box1 = findViewById(R.id.electionicCB);
        CheckBox box2 = findViewById(R.id.telecomCB);
        CheckBox box3 = findViewById(R.id.computerCB);
        Button sendBT = findViewById(R.id.sendBT);

        sendBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize intent and bundle
                Intent transIntent = new Intent(MainActivity.this, SubActivity.class);
                Bundle transBundle = new Bundle();
                //Get data with exception check
                try {
                    //Get Name
                    String name = nameET.getText().toString();
                    transBundle.putString("name", name);

                    //Get Student ID
                    int sid = Integer.parseInt(idET.getText().toString());
                    transBundle.putInt("sid", sid);

                    //Get Class
                    String cid = classET.getText().toString();
                    transBundle.putString("cid", cid);

                    //Get Phone Number
                    String pn = phoneNumET.getText().toString();
                    transBundle.putString("pn", pn);

                    //Get Seniority
                    int sen = 0;
                    if(opt1.isChecked()){
                        sen = 1;
                    } else if (opt2.isChecked()){
                        sen = 2;
                    } else if (opt3.isChecked()){
                        sen = 3;
                    } else if (opt4.isChecked()){
                        sen = 4;
                    }
                    transBundle.putInt("sen", sen);

                    //Get Majority
                    String mj = "";
                    if(box1.isChecked()){
                        mj += "Điện tử";
                    }
                    if(box2.isChecked()){
                        mj += "Viễn Thông";
                    }
                    if(box3.isChecked()){
                        mj += "Máy tính - Hệ thống nhúng";
                    }
                    transBundle.putString("mj", mj);

                    //Get plan
                    String plan = planET.getText().toString();
                    transBundle.putString("plan", plan);

                    transIntent.putExtra("MyData1", transBundle);
                } catch (Exception e) {
                    Log.d("MyApp", "User provided missing inputs");
                }
                startActivity(transIntent);
            }
        });
    }
}