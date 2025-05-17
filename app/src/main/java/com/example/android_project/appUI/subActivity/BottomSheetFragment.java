package com.example.android_project.appUI.subActivity;

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
        option1Button.setText(option1Text);
        option2Button.setText(option2Text);

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
}