package com.radproject.hms.global;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.radproject.hms.models.CropModel;
import com.radproject.hms.models.CultivationPlanModel;
import com.radproject.hms.models.FarmModel;
import com.radproject.hms.models.UserModel;

import java.util.ArrayList;

public class GlobalVariables {
    public static FirebaseAuth mAuth;
    public static String uid = null;
    public static UserModel currentUser = null;
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static ArrayList<CropModel> all_crop_list;
    public static ArrayList<FarmModel> all_farm_list;

//    public static ArrayList<CultivationPlanModel> all_cultivation_list;


//    public static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://wclaimer-538ab-default-rtdb.firebaseio.com/");
//
//    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
//    public static FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//            .setPersistenceEnabled(true)
//            .build();


}
