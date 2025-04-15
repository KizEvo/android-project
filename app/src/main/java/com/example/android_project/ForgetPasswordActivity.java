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
import java.util.concurrent.TimeUnit;

public class ForgetPasswordActivity extends AppCompatActivity {

    String [] users;
    int userCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fpwd), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button fpwdGetBtn = findViewById(R.id.fpwdGet);
        Button fpwdReturnBtn = findViewById(R.id.fpwdReturn);
        EditText emailInput = findViewById(R.id.fpwdEmail);

        TextView errorText = findViewById(R.id.fpwdErrorMsg);

        EditText recoverKeyInput = findViewById(R.id.fpwdRecoverKey);

        Intent intent = new Intent(ForgetPasswordActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();

        fpwdGetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
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
                            if (Objects.equals(users[i + 2], recoverKeyInput.getText().toString())) {
                                foundPassword = users[i + 1];
                                foundRecoverKey = users[i + 2];
                            }
                        }
                    }
                    // If email/password not found, raise error
                    if (Objects.equals(foundEmail, "") || Objects.equals(foundRecoverKey, "")) {
                        throw new Exception("Wrong email or recover key");
                    }
                    // Put user info to bundle
                    bundle.putStringArray("users", users);
                    bundle.putInt("userSize", userCount);
                    String out = "Password: " + foundPassword;
                    errorText.setText(out);
                    // Put bundle to intent
                    intent.putExtras(bundle);
                } catch (Exception e) {
                    String err = "ERROR: ";
                    err += e.getMessage();
                    errorText.setText(err);
                }
            }
        });
        fpwdReturnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If everything good, jump to login
                startActivity(intent);
            }
        });
    }
}