package com.radproject.hms.global;

import static com.radproject.hms.global.GlobalVariables.db;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PublicFirebaseMethods {

    static String cultivationPlanId;
        public static String generateCultivationPlanId(Context context) {
            // Get the user ID
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            // Get a reference to the Farmer document
            DocumentReference farmerRef = db.collection("Farmer").document(userId);

            // Get the user ID inside the Farmer document
            farmerRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        // Retrieve the user ID
                        String farmerUserId = documentSnapshot.getString("userId");

                        // Get a reference to the cultivation_plan collection for the user
                        CollectionReference cultivationPlanRef = db.collection("Farmer")
                                .document(farmerUserId).collection("cultivation_plan");

                        // Query the existing documents in the cultivation_plan collection
                        cultivationPlanRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                // Retrieve the count of existing documents
                                int documentCount = queryDocumentSnapshots.size();

                                // Generate the next cultivation plan ID using the user ID and the count
                                cultivationPlanId = farmerUserId + "_cul" + (documentCount + 1);

                                // Handle success
                                Toast.makeText(context, "Generated cultivation plan ID: " + cultivationPlanId, Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle failure
                                Toast.makeText(context, "Failed to generate cultivation plan ID: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        // Farmer document does not exist
                        Toast.makeText(context, "Farmer document does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Handle failure
                    Toast.makeText(context, "Failed to retrieve user ID from Farmer document: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            return cultivationPlanId;
        }

}
