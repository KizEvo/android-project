package com.example.android_project.appUI.subActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_project.R;

public class BannerClickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_banner_click);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bannerClickSV), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String choseBanner = getIntent().getStringExtra("choseBanner");
        if(choseBanner != null){
            if(choseBanner.equals("banner1")){

            } else if (choseBanner.equals("banner2")) {

            } else if (choseBanner.equals("banner3")) {

            } else if (choseBanner.equals("banner4")) {

            } else if (choseBanner.equals("banner5")) {

            }
        }

        //Back to HomePage
        Button backBT = findViewById(R.id.backBT);
        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}