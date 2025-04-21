package com.example.android_project.loginUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_project.R;

public class RegisterActivity extends AppCompatActivity {

    EditText EmailET, PasswordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registerActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EmailET = findViewById(R.id.EmailET);
        PasswordET = findViewById(R.id.PasswordET);

        Button LoginAccountBT = findViewById(R.id.LoginAccountBT);
        LoginAccountBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Jump to Login Activity (if already have account)
                login();
            }
        });

        Button RegisterBT = findViewById(R.id.RegisterBT);
        RegisterBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EmailET.getText().toString().trim();
                String password = PasswordET.getText().toString().trim();

                checkEmpty(email, password);

                //Get email and password from EditText fields
                //and verify with account stored in Database
                //... Implement Firebase here ...

            }
        });
    }

    //Jump to LoginActivity
    void login (){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    //Check empty fields
    private void checkEmpty(String email, String password) {
        //Check empty Email and Password
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Email/Password must not be empty!", Toast.LENGTH_SHORT).show();
        }
    }
}