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

public class SignupActivity extends AppCompatActivity {

    String [] users =  new String[300];

    int userCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.siup), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button siupRegisterBtn = findViewById(R.id.siupRegister);
        Button siupReturnBtn = findViewById(R.id.siupReturn);

        EditText siupEmailInput = findViewById(R.id.siupEmail);
        EditText siupPasswordInput = findViewById(R.id.siupPassword);
        EditText siupRecoverKeyInput = findViewById(R.id.siupRecoverKey);

        TextView siupErrorText = findViewById(R.id.siupErrorMsg);

        siupRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();

                    String email = siupEmailInput.getText().toString();
                    String password = siupPasswordInput.getText().toString();
                    String recoverKey = siupRecoverKeyInput.getText().toString();

                    if (users.length <= 0) {
                        // Sanity check
                        throw new Exception("No users buffer allocated yet");
                    }

                    if (email.isBlank() || password.isBlank() || recoverKey.isBlank()) {
                        throw new Exception("Fields cannot be empty");
                    }

                    users[userCount] = email;
                    users[userCount + 1] = password;
                    users[userCount + 2] = recoverKey;
                    userCount++;
                    // Put users to bundle
                    bundle.putStringArray("users", users);
                    bundle.putInt("userSize", userCount);
                    // Put bundle to intent
                    intent.putExtras(bundle);
                    // Register successful, jump to login
                    startActivity(intent);
                } catch (Exception e) {
                    String err = "ERROR: ";
                    err += e.getMessage();
                    siupErrorText.setText(err);
                }
            }
        });

        siupReturnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}