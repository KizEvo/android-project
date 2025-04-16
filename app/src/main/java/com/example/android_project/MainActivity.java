package com.example.android_project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static String API_KEY = "";
    private List<Message> messageList;
    private MessageAdapter adapter;

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

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // Instantiate components
        EditText editTextMessage = findViewById(R.id.editTextMessage);
        Button sendButton = findViewById(R.id.sendButton);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageList = new ArrayList<>();
        adapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(adapter);

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
                messageList.add(new Message(response, false));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(messageList.size() - 1);
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                // Handle error
                // User logic here, display a text or update something
                Log.e(TAG, error);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageList.add(new Message(error, false));
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputStr = String.valueOf(editTextMessage.getText());
                messageList.add(new Message(inputStr, true));
                adapter.notifyDataSetChanged();
                editTextMessage.setText("");

                restClient.post(inputStr, 500, API_KEY, callback);
            }
        });

    }
}