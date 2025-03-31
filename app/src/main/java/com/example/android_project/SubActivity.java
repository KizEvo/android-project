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
        // Get the inputs from MainActivity bundle
        Bundle bundle = getIntent().getExtras();

        TextView result = (TextView) findViewById(R.id.subTextView2);
        String output = "";
        if (bundle != null) {
            output += "\nYour name is " + bundle.getString("nameInput");
            output += "\nwith student id " + bundle.getString("idInput");
            output += "\nyou're in your " + bundle.getInt("yearBox") + " year";
            output += "\nyour class is " + bundle.getString("classInput");
            output += "\nyou're specialized in " + bundle.getString("specializedInput");
            output += "\nyour phone is " + bundle.getString("phoneInput");
            output += "\nand you plan to\n\t" + bundle.getString("planInput");
        }
        result.setText(output);
        Button btn = (Button) findViewById(R.id.button2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}