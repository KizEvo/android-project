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

public class HomeActivity extends AppCompatActivity {

    String userEmail;
    String userPassword;
    String userRecoverKey;

    String [] users;

    int userCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button homeLogoutBtn = findViewById(R.id.homeLogout);
        TextView homeText = findViewById(R.id.homeText);

        Intent intent = new Intent(HomeActivity.this, MainActivity.class);

        userEmail = getIntent().getStringExtra("email");

        String text = "You're " + userEmail;

        homeText.setText(text);

        users = getIntent().getStringArrayExtra("users");
        userCount = getIntent().getIntExtra("userSize", 0);

        homeLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putStringArray("users", users);
                bundle.putInt("userSize", userCount);

                // Put bundle to intent
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
}