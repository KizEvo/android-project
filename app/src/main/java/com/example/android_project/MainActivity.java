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

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    String [] users;

    int userCount;

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

        Button signUpBtn = findViewById(R.id.signUp);
        Button loginBtn = findViewById(R.id.login);
        TextView forgetPasswordText = findViewById(R.id.forgetPassword);

        EditText emailInput = findViewById(R.id.email);
        EditText passwordInput = findViewById(R.id.editTextTextPassword);

        TextView errorText = findViewById(R.id.errorMsg);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    Bundle bundle = new Bundle();
                    // Try to get users from signup
                    users = getIntent().getStringArrayExtra("users");
                    userCount = getIntent().getIntExtra("userSize", 0);

                    // Error when user first start the app
                    if (users == null || userCount <= 0) {
                        throw new Exception("User do not exist");
                    } else if (users.length % 3 != 0) {
                        // Sanity check
                        throw new Exception("System error, invalid user format");
                    }
                    // Get email/password with local storage
                    String foundEmail = "";
                    String foundPassword = "";
                    String foundRecoverKey = "";
                    int infoPerUser = userCount * 3;
                    for (int i = 0; i < infoPerUser; i += 3) {
                        if (users[i].isBlank()) {
                            break;
                        }
                        if (Objects.equals(users[i], emailInput.getText().toString())) {
                            foundEmail = users[i];
                            if (Objects.equals(users[i + 1], passwordInput.getText().toString())) {
                                foundPassword = users[i + 1];
                                foundRecoverKey = users[i + 2];
                            }
                        }
                    }
                    // If email/password not found, raise error
                    if (Objects.equals(foundEmail, "") || Objects.equals(foundPassword, "")) {
                        throw new Exception("Wrong email or password");
                    }
                    // Put user info to bundle
                    bundle.putString("email", foundEmail);
                    bundle.putString("password", foundPassword);
                    bundle.putString("recoverKey", foundRecoverKey);
                    bundle.putStringArray("users", users);
                    bundle.putInt("userSize", userCount);
                    // Put bundle to intent
                    intent.putExtras(bundle);
                    // If everything good, jump to home
                    startActivity(intent);
                } catch (Exception e) {
                    String err = "ERROR: ";
                    err += e.getMessage();
                    errorText.setText(err);
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        forgetPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MainActivity.this, ForgetPasswordActivity.class);
                    Bundle bundle = new Bundle();
                    // Try to get users from signup
                    users = getIntent().getStringArrayExtra("users");
                    userCount = getIntent().getIntExtra("userSize", 0);

                    if (users == null || userCount <= 0) {
                        throw new Exception("No user to recover password");
                    }

                    // Put user info to bundle
                    bundle.putStringArray("users", users);
                    bundle.putInt("userSize", userCount);

                    // Put bundle to intent
                    intent.putExtras(bundle);

                    startActivity(intent);
                } catch (Exception e) {
                    String err = "ERROR: ";
                    err += e.getMessage();
                    errorText.setText(err);
                }
            }
        });
    }
}