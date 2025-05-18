package com.example.android_project.appUI.subActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android_project.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    private String option1Text;
    private String option2Text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        // Get arguments
        if (getArguments() != null) {
            option1Text = getArguments().getString("OPTION_1");
            option2Text = getArguments().getString("OPTION_2");
        }

        // Set text dynamically
        Button option1Button = view.findViewById(R.id.opt1BT);
        Button option2Button = view.findViewById(R.id.opt2BT);

        // Set click ability base on state
        if(option1Text.equals("Not Available Yet")) {
            option1Button.setText(option1Text);
            option1Button.setClickable(false);
        } else {
            option1Button.setText(translateAirTimeStr(option1Text));
            // Button click listener
            option1Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SelectSeatActivity.class);
                    intent.putExtra("choseAir", option1Text);
                    startActivity(intent);
                }
            });
        }
        if(option2Text.equals("Not Available Yet")) {
            option2Button.setText(option2Text);
            option2Button.setClickable(false);
        } else {
            option2Button.setText(translateAirTimeStr(option2Text));
            // Button click listener
            option2Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SelectSeatActivity.class);
                    intent.putExtra("choseAir", option2Text);
                    startActivity(intent);
                }
            });
        }

        return view;
    }

    // Method to create instance with parameters
    public static BottomSheetFragment newInstance(String option1, String option2) {
        BottomSheetFragment fragment = new BottomSheetFragment();
        Bundle args = new Bundle();
        args.putString("OPTION_1", option1);
        args.putString("OPTION_2", option2);
        fragment.setArguments(args);
        return fragment;
    }

    // Translate air_202514051300 -> 13:00 - 14/05/2025
    private String translateAirTimeStr(String inputString) {
        // Assume that inputString has format of: air_202514051300
        //                                        0   4   8 ^ ^
        if(inputString.length() != 16) {
            return "Invalid Format airString";
        }
        String airTime = inputString.substring(12, 14) + ":" + inputString.substring(14);
        String airDate =  inputString.substring(8, 10) + "/"
                + inputString.substring(10, 12) + "/"
                + inputString.substring(4, 8);
        return airTime + " - " + airDate;
    }
}