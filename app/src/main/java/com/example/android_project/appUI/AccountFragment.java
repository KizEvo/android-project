package com.example.android_project.appUI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.HashMap;
import java.util.List;

public class AccountFragment extends Fragment {
    private String userName = "User";
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirestoreDocumentFetcher documentFetcher;
    private user userObject;
    private final String TAG = "AccountFragment";
    private FirebaseAuth mAuth;

    private ListView listView;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        mAuth = FirebaseAuth.getInstance();

        TextView nameTV = view.findViewById(R.id.accountName);
//        TextView movieTV = view.findViewById(R.id.accountMovie);
//        TextView roomTV = view.findViewById(R.id.accountRoom);
//        TextView slotTV = view.findViewById(R.id.accountSlot);
//        TextView airTV = view.findViewById(R.id.accountAir);
        listView = view.findViewById(R.id.myListView);

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
                        updateListView(userObject.getMovId(), userObject.getAirId(), userObject.getRoom(), userObject.getSlot());
//                        setTextView(movieTV, "Movie:", userObject.getMovId());
//                        setTextView(roomTV, "Room:", userObject.getRoom());
//                        setTextView(slotTV, "Slot:", userObject.getSlot());
//                        setTextView(airTV, "Air:", userObject.getAirId());
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

    private void updateListView(List<String> movID, List<String> airID, List<String> room, List<String> slot) {
        if(movID.isEmpty())
            return;

        HashMap<String, Integer> posterHM = new HashMap<>();
        posterHM.put("mov_86", R.drawable.poster_86);
        posterHM.put("mov_josee", R.drawable.poster_josee);
        posterHM.put("mov_violet", R.drawable.poster_violet);
        posterHM.put("mov_yourlie", R.drawable.poster_yourlie);
        posterHM.put("poster_yourname", R.drawable.poster_yourname);

        HashMap<String, String> titleHM = new HashMap<>();
        titleHM.put("mov_86", "Eighty Six");
        titleHM.put("mov_josee", "Josee, the Tiger and the Fish");
        titleHM.put("mov_violet", "Violet Evergarden");
        titleHM.put("mov_yourlie", "Your Lie in April");
        titleHM.put("poster_yourname", "Your Name");

        List<String> movieTitle = new ArrayList<>();
        List<Integer> moviePoster = new ArrayList<>();
        for(int i=0; i<movID.size(); i++) {
            movieTitle.add(titleHM.get(movID.get(i)));
            moviePoster.add(posterHM.get(movID.get(i)));
        }

        String[] labels = movieTitle.toArray(new String[0]);
        String[] descriptions1 = airID.toArray(new String[0]);
        String[] descriptions2 = new String[movID.size()];
        for(int i=0; i<movID.size(); i++){
           descriptions2[i] = room.get(i) + slot.get(i);
        }
        int[] icons = moviePoster.stream().mapToInt(Integer::intValue).toArray();

        CustomListAdapter adapter = new CustomListAdapter(getContext(), labels, descriptions1, descriptions2, icons);
        listView.setAdapter(adapter);

    }

}