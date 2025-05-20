package com.example.android_project.loginUI;

import com.example.android_project.appUI.FirestoreDocumentFetcher;
import com.example.android_project.appUI.object.user;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    EditText EmailET, PasswordET;
    private FirebaseAuth mAuth;
    private FirestoreDocumentFetcher documentFetcher;
    private String TAG;
    private String email;
    private String password;

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

        // Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Initialize the fetcher
        documentFetcher = new FirestoreDocumentFetcher(db);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        TAG = "RegisterActivity";

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
                email = EmailET.getText().toString().trim();
                password = PasswordET.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Email/Password must not be empty!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                //Get email and password from EditText fields
                //and verify with account stored in Database
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "createUserWithEmail:success");
                                    List<String> booked = new ArrayList<>();
                                    user userObject = new user(booked);
                                    documentFetcher.addMTBUserDocument(email, userObject, new FirestoreDocumentFetcher.DocumentAddCallback() {
                                        @Override
                                        public void onSuccess() {
                                            Log.d(TAG, "Added user to Firestore");
                                        }

                                        @Override
                                        public void onFailure(Exception e) {
                                            Log.d(TAG, "Failed to add user to Firestore");
                                        }
                                    });
                                    // Sign up success, jump to Login
                                    login();
                                } else {
                                    // If sign up fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    //Jump to LoginActivity
    void login (){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}