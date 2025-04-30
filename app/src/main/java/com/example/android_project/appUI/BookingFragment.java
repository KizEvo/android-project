package com.example.android_project.appUI;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.example.android_project.R;
import com.example.android_project.appUI.subActivity.BannerClickActivity;

public class BookingFragment extends Fragment {
    private int scrollPosition = 0;

    public BookingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        /*-----AUTO-SCROLL BEHAVIOR FOR SCROLLVIEW BANNERS-----*/
        Handler handler = new Handler();
        HorizontalScrollView scrollView = view.findViewById(R.id.horizontalScrollView);
        Runnable autoScrollRunnable = new Runnable() {
            @Override
            public void run() {
                if(scrollView != null) {
                    scrollPosition += 380;
                    if (scrollPosition >= 380 * 3) {
                        scrollPosition = 0; // Reset to the beginning
                    }
                    scrollView.smoothScrollTo(scrollPosition, 0);
                }
                handler.postDelayed(this, 3000); // Change delay as needed
            }
        };

        // Start auto-scrolling
        handler.postDelayed(autoScrollRunnable, 3000);

        /*-----CLICKABLE IMAGEVIEW BEHAVIOR-----*/
        setupBannerClickListeners(view);

        // Inflate the layout for this fragment
        return view;
    }

    //Click Listener
    private void setupBannerClickListeners(View view) {
        int[] bannerIds = {R.id.banner1, R.id.banner2, R.id.banner3, R.id.banner4, R.id.banner5};
        String[] bannerNames = {"banner1", "banner2", "banner3", "banner4", "banner5"};

        for (int i = 0; i < bannerIds.length; i++) {
            ImageView banner = view.findViewById(bannerIds[i]);
            final String bannerName = bannerNames[i];

            banner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), BannerClickActivity.class);
                    intent.putExtra("choseBanner", bannerName);
                    startActivity(intent);
                }
            });
        }
    }
}