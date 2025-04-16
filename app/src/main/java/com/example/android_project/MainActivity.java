package com.example.android_project;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static String API_KEY = "";
    private static String URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
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
        // Create OkHttp client with increased timeouts
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS) // Connection timeout
                .readTimeout(30, TimeUnit.SECONDS)    // Read timeout
                .writeTimeout(30, TimeUnit.SECONDS)   // Write timeout
                .build();
        FetchAIAPI api = new FetchAIAPI();
        API_KEY = api.fetchKey();
        // JSON payload
        String jsonPayload = "{\n" +
                "  \"contents\": [{\n" +
                "    \"parts\": [{\"text\": \"Explain how AI works\"}]\n" +
                "  }]\n" +
                "}";
        // Create request body
        RequestBody body = RequestBody.create(jsonPayload, JSON);

        // Build the request
        Request request = new Request.Builder()
                .url(URL + API_KEY)
                .header("Content-Type", "application/json")
                .post(body)
                .build();

        // Execute the request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle failure (e.g., network error)
                Log.e(TAG, "Request failed: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Handle successful response
                    String responseBody = response.body().string();
                    Log.d(TAG, "Response: " + responseBody);
                    // Process the response (e.g., parse JSON)
                    // Update UI if needed (use runOnUiThread if updating UI)
                    runOnUiThread(() -> {
                        // textView.setText(responseBody);
                    });
                } else {
                    // Handle error response
                    Log.e(TAG, "Error: " + response.code() + " " + response.message());
                }
            }
        });
    }
}