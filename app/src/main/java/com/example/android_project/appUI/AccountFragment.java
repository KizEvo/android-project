package com.example.android_project.appUI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android_project.loginUI.LoginActivity;
import com.example.android_project.loginUI.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.example.android_project.appUI.object.user;
import com.example.android_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment {

    private String userName = "User";
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirestoreDocumentFetcher documentFetcher;
    private user userObject;
    private final String TAG = "AccountFragment";
    private FirebaseAuth mAuth;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        mAuth = FirebaseAuth.getInstance();

        TextView nameTV = view.findViewById(R.id.accountName);
        TextView movieTV = view.findViewById(R.id.accountMovie);
        TextView roomTV = view.findViewById(R.id.accountRoom);
        TextView slotTV = view.findViewById(R.id.accountSlot);
        TextView airTV = view.findViewById(R.id.accountAir);

        Button logoutBt = view.findViewById(R.id.accountBt);

        // Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Initialize the fetcher
        documentFetcher = new FirestoreDocumentFetcher(db);

        if (currentUser != null) {
            // Set name
            String email = currentUser.getEmail();
            userName = email.substring(0, email.indexOf('@'));
            nameTV.setText(userName);
            // Get booked
            documentFetcher.fetchMTBUser(email, new FirestoreDocumentFetcher.DocumentReadCallback() {
                @Override
                public void onSuccess(DocumentSnapshot document) {
                    List<?> bookedArray = (List<?>) document.get("booked");
                    List<String> bookedList = new ArrayList<>();

                    if (bookedArray != null) {
                        for (Object item : bookedArray) {
                            if (item instanceof String) {
                                bookedList.add((String) item);
                            }
                        }
                        userObject = new user(bookedList);
                        setTextView(movieTV, "Movie:", userObject.getMovId());
                        setTextView(roomTV, "Room:", userObject.getRoom());
                        setTextView(slotTV, "Slot:", userObject.getSlot());
                        setTextView(airTV, "Air:", userObject.getAirId());
                        Log.d(TAG, "Fetch MTB user success: " + userName);
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "Failed to fetch MTB user: " + userName);
                }
            });
        }

        // Signout of Firebase and switch to Login acitivity
        logoutBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                mAuth.signOut();
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    static void setTextView(TextView tv, String prefix, List<String> l) {
        String out = prefix;
        for (String str : l) {
            out += str + ",";
        }
        tv.setText(out);
    }

}