package com.example.android_project.appUI;

import com.example.android_project.appUI.object.air;
import com.example.android_project.appUI.object.user;
import android.util.Log;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreDocumentFetcher {
    private static final String TAG = "FirestoreDocumentFetcher";
    private FirebaseFirestore db;
    private final String userColl = "MTBUser";
    private final String airColl = "MTBAir";
    private final String movieColl = "MTBMovie";

    public interface DocumentReadCallback {
        void onSuccess(DocumentSnapshot document);
        void onFailure(Exception e);
    }

    // Callback interface for handling collection fetch results
    public interface CollectionCallback {
        void onSuccess(List<DocumentSnapshot> documents);
        void onFailure(Exception e);
    }

    public interface DocumentAddCallback {
        void onSuccess();
        void onFailure(Exception e);
    }

    public FirestoreDocumentFetcher(FirebaseFirestore firestore) {
        this.db = firestore;
    }

    // Fetch a document
    public void fetchDocument(String collectionPath, String documentId, @NonNull DocumentReadCallback callback) {
        DocumentReference docRef = db.collection(collectionPath).document(documentId);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    Log.d(TAG, "Document read successfully!");
                    callback.onSuccess(document);
                } else {
                    Log.d(TAG, "Document does not exist!");
                    callback.onFailure(new Exception("Document does not exist"));
                }
            } else {
                Log.d(TAG, "Something went wrong while reading document!");
                callback.onFailure(task.getException());
            }
        });
    }

    // Fetch all documents from a collection
    public void fetchAllDocuments(String collectionPath, @NonNull CollectionCallback callback) {
        db.collection(collectionPath).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    Log.e(TAG, "Fetch all document successfully");
                    callback.onSuccess(querySnapshot.getDocuments());
                } else {
                    Log.e(TAG, "Empty document");
                    callback.onSuccess(new ArrayList<>()); // No document
                }
            } else {
                Log.e(TAG, "Error fetching collection", task.getException());
                callback.onFailure(task.getException());
            }
        });
    }
    // Fetch document
    public void fetchMTBUser(String documentId, @NonNull DocumentReadCallback callback) {
        fetchDocument(userColl, documentId, callback);
    }

    // Fetch all x document
    public void fetchAllMTBAir(@NonNull CollectionCallback callback) {
        fetchAllDocuments(airColl, callback);
    }
    public void fetchAllMTBMovie(@NonNull CollectionCallback callback) {
        fetchAllDocuments(movieColl, callback);
    }
    public void fetchAllMTBUser(@NonNull CollectionCallback callback) {
        fetchAllDocuments(userColl, callback);
    }

    // Add a document
    public void addDocument(String collectionPath, String documentId, Map<?, ?> data, @NonNull DocumentAddCallback callback) {
        db.collection(collectionPath).document(documentId)
        .set(data)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "DocumentSnapshot successfully written!");
                callback.onSuccess();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Error writing document");
                callback.onFailure(new Exception("Error writing document"));
            }
        });
    }

    /* Fetch MTB* document */
    //
    // This overwrite the existing document
    // To update existing document, keep all entry the same and only update/add new in "input" parameter
    // For example, first we fetch the MTBAir document and store it in "class air". When we want to
    // update the slot[], then we can modify the object in local and pass it as input to this function
    // to update the database

    // documentId follows this format: mov_<short_name>, for example: mov_josee
    public void addMTBAirDocument(String documentId, air input, @NonNull DocumentAddCallback callback) {
        Map<String, Object> data = new HashMap<>();
        data.put("movie", input.getMovie());
        data.put("room", input.getRoom());
        data.put("slot", input.getSlot());
        addDocument(airColl, documentId, data, callback);
    }
    // documentId is the user gmail, for example: jackson@gmail.com
    public void addMTBUserDocument(String documentId, user input, @NonNull DocumentAddCallback callback) {
        Map<String, Object> data = new HashMap<>();
        data.put("booked", input.getBooked());
        addDocument(userColl, documentId, data, callback);
    }
}