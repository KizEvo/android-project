package com.example.android_project;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static String API_KEY = "";

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
        // Get REST API keys
        FetchAIAPI api = new FetchAIAPI();
        API_KEY = api.fetchKey();
        // Initialize RestClient with read/write and connectTimeout timeout value of 40 seconds
        RestClient restClient = new RestClient(40);

        // Define callback, this will get called if RestClient returns successfully
        // Callback is needed because HTTP requests are async.
        RestCallback callback = new RestCallback() {
            @Override
            public void onSuccess(String response) {
                // Handle successful response
                // User logic here, display a text or update something
                Log.d(TAG, "Response: " + response);
            }

            @Override
            public void onFailure(String error) {
                // Handle error
                // User logic here, display a text or update something
                Log.e(TAG, error);
            }
        };

        // Call POST request with callback
        // maxOutputTokens limits the generated text from model -> limit model free quota
        // Pass callback to the `post` method, which will get called when an event occur.
        restClient.post("What is AI ?", 500, API_KEY, callback);
    }
}