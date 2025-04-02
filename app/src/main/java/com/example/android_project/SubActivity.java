package com.example.android_project;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SubActivity extends AppCompatActivity {
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Get the image as a Bitmap
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = null;
            if (extras != null) {
                imageBitmap = (Bitmap) extras.get("data");
            } else {
                return;
            }
            // Display the image in an ImageView
            ImageView imageView = findViewById(R.id.avatarIV);
            imageView.setImageBitmap(imageBitmap);
        }
    }
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
        Button callBT = findViewById(R.id.callBT);
        Button smsBT = findViewById(R.id.smsBT);
        Button cameraBT = findViewById(R.id.cameraBT);

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
        callBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the phone number from EditText
                String phoneNumber = phoneNumTV.getText().toString().trim();
                // Check if the phone number is empty
                if (phoneNumber.isEmpty()) {
                    return;
                }
                // Create the Intent for making a call
                Intent call_intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                // Check for CALL_PHONE permission
                if (ActivityCompat.checkSelfPermission(SubActivity.this,
                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // Request permission from the user
                    ActivityCompat.requestPermissions(SubActivity.this,
                            new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                // Start the call if permission is granted
                startActivity(call_intent);
            }
        });
        smsBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the phone number from EditText
                String phoneNumber = phoneNumTV.getText().toString().trim();
                String message = nameTV.getText().toString() + "\n"
                        + sidTV.getText().toString() + "\n"
                        + classTV.getText().toString() + " \n"
                        + phoneNumTV.getText().toString() + "\n"
                        + seniorityTV.getText().toString() + "\n"
                        + majorityTV.getText().toString() + "\n"
                        + planTV.getText().toString() + "\n";
                // Check if the message is empty
                if (phoneNumber.isEmpty()) {
                    return;
                }
                // Open the default SMS app with user input message
                Intent sms_intent = new Intent(Intent.ACTION_VIEW);
                sms_intent.setData(Uri.parse("smsto:" + phoneNumber));
                sms_intent.putExtra("sms_body", message);

                startActivity(sms_intent);
            }
        });
        cameraBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check Camera Permission
                if (ContextCompat.checkSelfPermission(SubActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SubActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    return;
                }
                // Open Camera
                Intent takePictureIntent = new Intent(ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, 1);
            }
        });
    }
}
