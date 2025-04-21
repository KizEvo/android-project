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

public class LoginActivity extends AppCompatActivity {

    EditText EmailET, PasswordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EmailET = findViewById(R.id.EmailET);
        PasswordET = findViewById(R.id.PasswordET);

        Button CreateAccountBT = findViewById(R.id.CreateAccountBT);
        CreateAccountBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Jump to Register activity (if don't have account)
                register();
            }
        });

        Button LoginBT = findViewById(R.id.LoginBT);
        LoginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EmailET.getText().toString().trim();
                String password = PasswordET.getText().toString().trim();

                checkEmpty(email, password);

                //Get email and password from EditText fields
                //and verify with account stored in Database
                //... Implement Firebase here ...

                //Get into the appUI if login successfully
            }
        });
    }

    //Jump to RegisterActivity
    void register (){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    //Check empty fields
    private void checkEmpty(String email, String password) {
        //Check empty Email and Password
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Email/Password must not be empty!", Toast.LENGTH_SHORT).show();
        }
    }
}