package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SubActivity extends AppCompatActivity {
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

        Button backBT = findViewById(R.id.backBT);

        //Link object by component ID
        TextView nameTV = findViewById(R.id.nameTV);
        TextView sidTV = findViewById(R.id.sidTV);
        TextView classTV = findViewById(R.id.classTV);
        TextView phoneNumTV = findViewById(R.id.phoneNumTV);
        TextView seniorityTV = findViewById(R.id.seniorityTV);
        TextView majorityTV = findViewById(R.id.majorityTV);
        TextView planTV = findViewById(R.id.planTV);

        //Get bundle
        Bundle bundle = getIntent().getBundleExtra("MyData1");

        //Get data and display
        if(bundle != null){
            nameTV.setText(bundle.getString("name"));
            sidTV.setText(String.valueOf(bundle.getInt("sid")));
            classTV.setText(bundle.getString("cid"));
            phoneNumTV.setText(bundle.getString("pn"));
            seniorityTV.setText(String.valueOf(bundle.getInt("sen")));
            majorityTV.setText(bundle.getString("mj"));
            planTV.setText(bundle.getString("plan"));
        }

        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubActivity.this, com.example.android_project.MainActivity.class);
                startActivity(intent);
            }
        });
    }
}