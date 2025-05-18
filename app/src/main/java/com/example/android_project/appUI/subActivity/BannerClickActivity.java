package com.example.android_project.appUI.subActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_project.R;
import com.example.android_project.appUI.FirestoreDocumentFetcher;
import com.example.android_project.appUI.object.movie;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BannerClickActivity extends AppCompatActivity {
    private HashMap<String, movie> movieList = new HashMap<String, movie>();
    private FirestoreDocumentFetcher documentFetcher;
    private static final String TAG = "BannerClickActivity";
    private String choseBanner;
    private TextView directorTV, actorTV, categoryTV, releaseDateTV, durationTV, titleTV, descriptionTV;
    private ImageView posterIV;
    private Button bookingBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_banner_click);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bannerClickSV), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        titleTV = findViewById(R.id.titleTV);
        posterIV = findViewById(R.id.posterIV);
        directorTV = findViewById(R.id.directorTV);
        actorTV = findViewById(R.id.actorTV);
        categoryTV = findViewById(R.id.categoryTV);
        releaseDateTV = findViewById(R.id.releaseDateTV);
        durationTV = findViewById(R.id.durationTV);
        descriptionTV = findViewById(R.id.descriptionTV);

        // Get Intent from BookingFragment
        choseBanner = getIntent().getStringExtra("choseBanner");
        Log.d("DEBUG1", "choseBanner = " + choseBanner);

        // Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Initialize the fetcher
        documentFetcher = new FirestoreDocumentFetcher(db);

        // Fetch movie data from Firestore/MTBMovie
        fetchMovieData();

        // Fetch air time from Firestore/MTBAir
        fetchAirData();

        // Back to HomePage
        Button backBT = findViewById(R.id.backBT);
        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void fetchMovieData() {
        // Fetch all documents from the "MTBMovie" collection
        documentFetcher.fetchAllDocuments("MTBMovie", new FirestoreDocumentFetcher.CollectionCallback() {
            @Override
            public void onSuccess(List<DocumentSnapshot> documents) {
                Log.d("Firestore", "Fetched " + documents.size() + " documents");
                if (documents.isEmpty()) {
                    Log.d(TAG, "Empty document");
                    return;
                }

                // Process the documents
                for (DocumentSnapshot document : documents) {
                    // Get ID
                    String documentId = document.getId();
                    // Get posterName
                    String posterName = document.getString("posterName");
                    // Get movieName
                    String movieName = document.getString("movieName");
                    // Get directorName
                    String directorName = document.getString("directorName");
                    // Get casterName array
                    List<String> casterNameList = (List<String>) document.get("casterName");
                    String[] casterName = {""};
                    if (casterNameList != null) {
                        casterName = casterNameList.toArray(new String[0]);
                    }
                    // Get category
                    String category = document.getString("category");
                    // Get debutDate
                    String debutDate = document.getString("debutDate");
                    // Get duration
                    Number durationNum = (Number) document.get("duration");
                    int duration = durationNum != null ? durationNum.intValue() : 0;
                    // Get language
                    String language = document.getString("language");

                    // Create and store movie objects
                    List <String> emptyList= new ArrayList<>();
                    movie x = new movie(documentId, posterName, movieName, directorName, casterName, category, debutDate, duration, language, emptyList);
                    movieList.put(documentId, x);

                    Log.d(TAG, "Document ID: " + documentId + ", poster name: " + posterName + ", movie name: " + movieName);
                }

                // Update the UI after fetching
                updateUI();

            }

            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "Error fetching collection", e);
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
                    String airTime = document.getId();
                    // Get movie list
                    List<String> movieIDList = (List<String>) document.get("movie");
                    if(movieIDList != null) {
                        for (String movieID : movieIDList) {
                            if(movieList.containsKey(movieID)){
                                movieList.get(movieID).addAirTimeStr(airTime);
                                Log.d(TAG, "MovieID: " + movieID + ", Air Time: " + airTime);
                            }
                        }
                    }
                }

                // Update option labels
                bookingBT = findViewById(R.id.bookingBT);
                bookingBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String labelBT0 = "Not Available Yet";
                        String labelBT1 = "Not Available Yet";
                        if (choseBanner != null && movieList.containsKey(choseBanner)) {
                            List<String> airTimeList = movieList.get(choseBanner).getAirTimeStr();
                            if(airTimeList.size() >= 1)
                                labelBT0 = airTimeList.get(0);
                            if(airTimeList.size() >= 2)
                                labelBT1 = airTimeList.get(1);
                        }

                        BottomSheetFragment bottomSheetFragment = BottomSheetFragment.newInstance(labelBT0, labelBT1);
                        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "Error fetching collection", e);
            }
        });
    }

    private void updateUI() {
        if (choseBanner != null && movieList.containsKey(choseBanner)) {
            movie x = movieList.get(choseBanner);
            Log.d("DEBUG2", "Found movie: " + x.getMovieName());

            // Update UI with movie data
            titleTV.setText(x.getMovieName());
            directorTV.setText(x.getDirectorName());
            actorTV.setText(x.getConcatenateCasterName());
            categoryTV.setText(x.getCategory());
            releaseDateTV.setText(x.getDebutDate());
            durationTV.setText(String.valueOf(x.getDuration()) + " min");

            //Set the poster image and description
            if(x.getPosterName().equals("poster_86")){
                posterIV.setImageResource(R.drawable.poster_86);
                descriptionTV.setText(R.string.mov_86_description);
            } else if (x.getPosterName().equals("poster_josee")){
                posterIV.setImageResource(R.drawable.poster_josee);
                descriptionTV.setText(R.string.mov_josee_description);
            } else if (x.getPosterName().equals("poster_violet")){
                posterIV.setImageResource(R.drawable.poster_violet);
                descriptionTV.setText(R.string.mov_violet_description);
            } else if (x.getPosterName().equals("poster_yourlie")){
                posterIV.setImageResource(R.drawable.poster_yourlie);
                descriptionTV.setText(R.string.mov_yourlie_description);
            } else if (x.getPosterName().equals("poster_yourname")){
                posterIV.setImageResource(R.drawable.poster_yourname);
                descriptionTV.setText(R.string.mov_yourname_description);
            } else {
                // Use default Poster and Description
            }
        } else {
            Log.e(TAG, "Movie not found in movieList: " + choseBanner);
            Log.d(TAG, "Available movies: " + movieList.keySet());
        }
    }

}