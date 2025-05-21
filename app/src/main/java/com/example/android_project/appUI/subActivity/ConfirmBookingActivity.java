package com.example.android_project.appUI.subActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_project.R;
import com.example.android_project.appUI.FirestoreDocumentFetcher;
import com.example.android_project.appUI.object.user;
import com.example.android_project.loginUI.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ConfirmBookingActivity extends AppCompatActivity {
    private String movTitle, choseAir, roomNum, seatNum, airTime, movID, ticketID;
    private TextView movieTitleTV, airDateTV, airTimeTV, roomNumberTV, seatNumTV, ticketIDTV;
    private String email;
    private FirestoreDocumentFetcher documentFetcher;
    private static final String TAG = "SelectSeatActivity";
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirm_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.confirmBookingLL), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        choseAir = getIntent().getStringExtra("choseAir");
        seatNum = getIntent().getStringExtra("seatNum");
        movTitle = getIntent().getStringExtra("movTitle");
        movID = getIntent().getStringExtra("movID");

        // Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Initialize the fetcher
        documentFetcher = new FirestoreDocumentFetcher(db);

        // Fetch seat state data
        fetchAirData();

        movieTitleTV = findViewById(R.id.movieTitleTV);
        airDateTV = findViewById(R.id.airDateTV);
        airTimeTV = findViewById(R.id.airTimeTV);
        roomNumberTV = findViewById(R.id.roomNumberTV);
        seatNumTV = findViewById(R.id.seatNumTV);
        ticketIDTV = findViewById(R.id.ticketIDTV);

        //Back to SelectSeatActivity
        Button returnBT = findViewById(R.id.returnBT);
        returnBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Confirm booking button
        Button confirmBT = findViewById(R.id.confirmBT);
        confirmBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send ticketID to MTB User under username
                sendBookedArray();
                //Notification
                Toast.makeText(ConfirmBookingActivity.this, "Booked ticket successfully!",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void fetchAirData() {
        // Fetch all documents from the "MTBAir" collection
        documentFetcher.fetchAllDocuments("MTBAir", new FirestoreDocumentFetcher.CollectionCallback() {
            @Override
            public void onSuccess(List<DocumentSnapshot> documents) {
                Log.d("Firestore", "Fetched " + documents.size() + " documents");
                if (documents.isEmpty()) {
                    Log.d(TAG, "Empty document");
                    return;
                }

                // Process the documents
                for (DocumentSnapshot document : documents) {
                    // Get ID (Air Time String)
                    airTime = document.getId();
                    if(airTime.equals(choseAir)){
                        List<String> roomList = (List<String>) document.get("room");
                        if(roomList != null) {
                            roomNum = roomList.get(0);
                        }
                        break;
                    }
                }

                // Update UI
                updateUI();
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "Error fetching collection", e);
            }
        });
    }

    private void sendBookedArray() {
        if(currentUser == null) {
            return;
        }
        email = currentUser.getEmail();

        // Fetch all documents from the "MTBAir" collection
        documentFetcher.fetchAllDocuments("MTBUser", new FirestoreDocumentFetcher.CollectionCallback() {
            @Override
            public void onSuccess(List<DocumentSnapshot> documents) {
                Log.d("Firestore", "Fetched " + documents.size() + " documents");
                if (documents.isEmpty()) {
                    Log.d(TAG, "Empty document");
                    return;
                }

                // Process the documents
                for (DocumentSnapshot document : documents) {
                    // Get userEmail
                    String userEmail = document.getId();
                    if(userEmail.equals(email)){
                        List<String> bookedList = (List<String>) document.get("booked");
                        bookedList.add(ticketID);
                        user userObj = new user(bookedList);
                        documentFetcher.addMTBUserDocument(userEmail, userObj, new FirestoreDocumentFetcher.DocumentAddCallback() {
                            @Override
                            public void onSuccess() {
                                Log.d(TAG, "Added user to Firestore");
                            }

                            @Override
                            public void onFailure(Exception e) {
                                Log.d(TAG, "Failed to add user to Firestore");
                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "Error fetching collection", e);
            }
        });
    }

    private void updateUI() {
        ticketID = movID + "-" + airTime + "-" + roomNum + "-slot_" + seatNum;

        // Assume that: air_202514051300
        //              0   4       12
        String tmpDateStr = airTime.substring(4, 8) + "/" + airTime.substring(8, 10) + "/" + airTime.substring(10, 12);
        String tmpTimeStr = airTime.substring(12, 14) + ":" + airTime.substring(14);
        // Update UI
        movieTitleTV.setText(movTitle);
        airDateTV.setText(tmpDateStr);
        seatNumTV.setText(seatNum);
        airTimeTV.setText(tmpTimeStr);
        roomNumberTV.setText(roomNum);
        ticketIDTV.setText(ticketID);
    }

}