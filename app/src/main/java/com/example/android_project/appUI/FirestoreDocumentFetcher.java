package com.example.android_project.appUI;

import android.util.Log;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirestoreDocumentFetcher {
    private static final String TAG = "FirestoreDocumentFetcher";
    private FirebaseFirestore db;

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
}