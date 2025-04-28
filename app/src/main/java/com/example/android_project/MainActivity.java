package com.example.android_project;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
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
        Activity activity = this;
        String[] text = {"Android Cupcakes", "Android Flamingo", "Android Small", "Android Big", "Android Medium", "Android Cockatiel", "Android Lovebird"};
        String[] textVersion = {"Version 1.2", "Version 1.3", "Version 1.4", "Version 1.5", "Version 1.6", "Version 1.7", "Version 1.8"};
        int[] resId = {R.drawable.android, R.drawable.android1, R.drawable.android2, R.drawable.android1, R.drawable.android, R.drawable.android2, R.drawable.android1};

        listView = (ListView) findViewById(R.id.list);
        Component adapter = new Component(this, text, textVersion, resId, 0);
        listView.setAdapter(adapter);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] newText = {"Orange", "Cherry", "Banana", "Apple"};
                String[] newTextVersion = {"47 Calories", "50 Calories", "89 Calories", "52 Calories"};
                int[] newResId = {R.drawable.orange, R.drawable.cherry, R.drawable.banana, R.drawable.apple};
                LinearLayout viewLL = (LinearLayout) findViewById(R.id.main);
                viewLL.setBackgroundColor(Color.DKGRAY);
                Component newAdapter = new Component(activity, newText, newTextVersion, newResId, 1);
                listView.setAdapter(newAdapter);
            }
        });
    }
}