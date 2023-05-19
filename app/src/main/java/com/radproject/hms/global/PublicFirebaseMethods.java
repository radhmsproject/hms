package com.radproject.hms.global;

import static com.radproject.hms.global.GlobalVariables.db;
import static com.radproject.hms.global.GlobalVariables.uid;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.radproject.hms.models.CropModel;
import com.radproject.hms.models.CultivationPlanModel;

import java.util.ArrayList;
import java.util.List;

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
                                cultivationPlanId = "Cul" + (documentCount + 1)+"_"+farmerUserId;
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


    public static ArrayList<CultivationPlanModel> getAllCultivationForCurrentFarmer() {
        CollectionReference cultivationPlanRef = db.collection("Farmer").document(uid).collection("cultivation_plan");
        ArrayList<CultivationPlanModel> cultivationList = new ArrayList<>();
        cultivationPlanRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        CultivationPlanModel cropModel = document.toObject(CultivationPlanModel.class);
                        cultivationList.add(cropModel);

                    }
                } else {
                    Log.e("Firestore", "Error getting crops: " + task.getException());
                }
            }
        });
        return cultivationList;
    }

}
