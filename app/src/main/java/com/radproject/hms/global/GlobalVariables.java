package com.radproject.hms.global;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.radproject.hms.models.CropModel;
import com.radproject.hms.models.FarmModel;
import com.radproject.hms.models.UserModel;

import java.util.ArrayList;

public class GlobalVariables {
    public static FirebaseAuth mAuth;
    public static String uid = null;
    public static UserModel currentUser = null;
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static ArrayList<CropModel> crop_list;
    public static ArrayList<FarmModel> get_farmList;


//    public static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://wclaimer-538ab-default-rtdb.firebaseio.com/");
//
//    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
//    public static FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//            .setPersistenceEnabled(true)
//            .build();


}
