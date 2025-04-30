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
import android.widget.VideoView;

import com.example.android_project.R;
import com.example.android_project.appUI.subActivity.BannerClickActivity;

public class HomeFragment extends Fragment {
    private VideoView videoViewBg;
    private int currentVideoPosition = 0;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

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

}