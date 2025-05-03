package com.example.android_project.appUI;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.android_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeFragment extends Fragment {
    private VideoView videoViewBg;
    private TextView welcomeTV;
    private Handler handler = new Handler();

    private int currentVideoPosition = 0;
    private int i = 0;
    private String userName = "User";
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        /*-----WELCOME TEXT WITH ANIMATION-----*/
        welcomeTV = view.findViewById(R.id.welcomeTV);
        if(currentUser != null) {
            String email = currentUser.getEmail();
            userName = email.substring(0, email.indexOf('@'));
        }
        welcomeLineWithAnimation("Welcome \""+ userName + "\"!");

        /*-----LOOPING INTRO VIDEO-----*/
        videoViewBg = view.findViewById(R.id.videoViewBg);
        Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.intro);
        videoViewBg.setVideoURI(uri);

        videoViewBg.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                videoViewBg.seekTo(currentVideoPosition);
                videoViewBg.start();
            }
        });

        return view;
    }

    //Pause and save video playing process
    @Override
    public void onPause() {
        super.onPause();
        currentVideoPosition = videoViewBg.getCurrentPosition();
        videoViewBg.pause();
    }

    //Continue playing video
    @Override
    public void onResume() {
        super.onResume();
        videoViewBg.seekTo(currentVideoPosition);
        videoViewBg.start();
    }

    //Animate welcome text
    private void welcomeLineWithAnimation(String textToAnimate) {
        welcomeTV.setText("");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(i < textToAnimate.length()) {
                    welcomeTV.setText(welcomeTV.getText().toString() + textToAnimate.charAt(i));
                    i++;
                    handler.postDelayed(this, 75);
                }
            }
        }, 150);
    }

}