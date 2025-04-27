package com.example.android_project.appUI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.example.android_project.R;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//        }
//    }

    int scrollPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        /*-----AUTO-SCROLL BEHAVIOR FOR SCROLLVIEW BANNERS-----*/
        Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                HorizontalScrollView scrollView = view.findViewById(R.id.horizontalScrollView);
                scrollPosition += 380;
                Log.d("DEBUG", "scrollPos = " + scrollPosition + "Boundary = " + scrollView.getChildAt(0).getWidth());
                if (scrollPosition >= 380*3) {
                    scrollPosition = 0; // Reset to the beginning
                }
                scrollView.smoothScrollTo(scrollPosition, 0);
                handler.postDelayed(this, 3000); // Change delay as needed
            }
        };

        // Start auto-scrolling
        handler.postDelayed(runnable, 3000);

        /*-----CLICKABLE IMAGEVIEW BEHAVIOR-----*/
        ImageView banner1 = view.findViewById(R.id.banner1);
        ImageView banner2 = view.findViewById(R.id.banner2);
        ImageView banner3 = view.findViewById(R.id.banner3);
        ImageView banner4 = view.findViewById(R.id.banner4);
        ImageView banner5 = view.findViewById(R.id.banner5);

        banner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BannerClickActivity.class);
                intent.putExtra("choseBanner", "banner1");
                startActivity(intent);
            }
        });

        banner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BannerClickActivity.class);
                intent.putExtra("choseBanner", "banner2");
                startActivity(intent);
            }
        });

        banner3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BannerClickActivity.class);
                intent.putExtra("choseBanner", "banner3");
                startActivity(intent);
            }
        });

        banner4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BannerClickActivity.class);
                intent.putExtra("choseBanner", "banner4");
                startActivity(intent);
            }
        });

        banner5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BannerClickActivity.class);
                intent.putExtra("choseBanner", "banner5");
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}