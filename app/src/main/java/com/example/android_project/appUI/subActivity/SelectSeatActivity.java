package com.example.android_project.appUI.subActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_project.R;
import com.example.android_project.appUI.FirestoreDocumentFetcher;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class SelectSeatActivity extends AppCompatActivity {
    private String choseAir;
    private String movID;
    private String movTitle;
    private Boolean[] seatState = new Boolean[32];
    private FirestoreDocumentFetcher documentFetcher;
    private static final String TAG = "SelectSeatActivity";
    private boolean isExpandedZoneA = false;
    private boolean isExpandedZoneB = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_seat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.selectSeatSV), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get Intent from BookingFragment
        choseAir = getIntent().getStringExtra("choseAir");
        movID = getIntent().getStringExtra("movID");
        movTitle = getIntent().getStringExtra("movTitle");

        // Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Initialize the fetcher
        documentFetcher = new FirestoreDocumentFetcher(db);

        // Fetch seat state data
        fetchSeatStateData();

        // Expand zoneA map
        GridLayout zoneAGL = findViewById(R.id.zoneAGL);
        Button zoneAToggleBT = findViewById(R.id.zoneAToggleBT);
        zoneAToggleBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpandedZoneA) {
                    zoneAGL.setVisibility(View.GONE);
                    zoneAToggleBT.setText("▼ Zone A");
                } else {
                    zoneAGL.setVisibility(View.VISIBLE);
                    zoneAToggleBT.setText("▲ Zone A");
                }
                isExpandedZoneA = !isExpandedZoneA;
            }
        });

        // Expand zoneB map
        Button zoneBToggleBT = findViewById(R.id.zoneBToggleBT);
        GridLayout zoneBGL = findViewById(R.id.zoneBGL);
        zoneBToggleBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpandedZoneB) {
                    zoneBGL.setVisibility(View.GONE);
                    zoneBToggleBT.setText("▼ Zone B");
                } else {
                    zoneBGL.setVisibility(View.VISIBLE);
                    zoneBToggleBT.setText("▲ Zone B");
                }
                isExpandedZoneB = !isExpandedZoneB;
            }
        });

        // Back to BannerClickActivity
        Button backToBannerClickBT = findViewById(R.id.backToBannerClickBT);
        backToBannerClickBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void fetchSeatStateData() {
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
                    String airTime = document.getId();
                    if(airTime.equals(choseAir)){
                        List<Boolean> slotList = (List<Boolean>) document.get("slot");
                        if(slotList != null) {
                            for (int i = 0; i < slotList.size(); i++) {
                                seatState[i] = slotList.get(i);
                            }
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

    private void updateUI () {
        int[] seatIds = {R.id.seat1BT, R.id.seat2BT, R.id.seat3BT, R.id.seat4BT,
                         R.id.seat5BT, R.id.seat6BT, R.id.seat7BT, R.id.seat8BT,
                         R.id.seat9BT, R.id.seat10BT, R.id.seat11BT, R.id.seat12BT,
                         R.id.seat13BT, R.id.seat14BT, R.id.seat15BT, R.id.seat16BT,
                         R.id.seat17BT, R.id.seat18BT, R.id.seat19BT, R.id.seat20BT,
                         R.id.seat21BT, R.id.seat22BT, R.id.seat23BT, R.id.seat24BT,
                         R.id.seat25BT, R.id.seat26BT, R.id.seat27BT, R.id.seat28BT,
                         R.id.seat29BT, R.id.seat30BT, R.id.seat31BT, R.id.seat32BT};

        for (int i = 0; i < 32; i++) {
            Button seatBT = findViewById(seatIds[i]);

            final String seatNum = String.valueOf(i+1);

            if(seatState[i]) {
                seatBT.setBackgroundResource(R.drawable.round_rectangle_seat_button_full);
                seatBT.setClickable(false);
            } else {
                seatBT.setBackgroundResource(R.drawable.round_rectangle_seat_button_empty);
                seatBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SelectSeatActivity.this, ConfirmBookingActivity.class);
                        intent.putExtra("seatNum", seatNum);
                        intent.putExtra("choseAir", choseAir);
                        intent.putExtra("movTitle", movTitle);
                        intent.putExtra("movID", movID);
                        startActivity(intent);
                    }
                });
            }

        }
    }
}