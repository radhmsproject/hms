package com.radproject.hms.global;

import static android.content.ContentValues.TAG;

import static com.radproject.hms.global.GlobalVariables.db;
import static com.radproject.hms.global.GlobalVariables.mAuth;
import static com.radproject.hms.global.GlobalVariables.uid;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.radproject.hms.LoginActivity;
import com.radproject.hms.MainActivity;
import com.radproject.hms.RegisterActivity;
import com.radproject.hms.models.CropModel;
import com.radproject.hms.models.CultivationPlanModel;
import com.radproject.hms.models.FarmModel;
import com.radproject.hms.models.UserModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class GlobalMethods {

    public static void redirectToRegister(Activity activity) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void redirectToLogin(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void redirectToDashboard(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void Logout(Activity activity) {
        FirebaseAuth.getInstance().signOut();
        redirectToLogin(activity);
    }

    //    public static void mapUser(String uid) {
//        Log.e(TAG, "mapUser");
//        GlobalVariables.db.collection("Farmer")
//                .document(uid)
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        DocumentSnapshot document = task.getResult();
//                        if (document.exists()) {
//                            GlobalVariables.currentUser = document.toObject(UserModel.class);
//                            Log.e(TAG, "mapUser: " + GlobalVariables.currentUser.getUsername());
//                        } else {
//                            Log.d(TAG, "No such document");
//                        }
//                    } else {
//                        Log.d(TAG, "Error getting document: ", task.getException());
//                    }
//                });
//    }
    public static void listenForUserChanges(String uid) {
        Log.e(TAG, "listenForUserChanges");
        db.collection("Farmer")
                .document(uid)
                .addSnapshotListener((document, error) -> {
                    if (error != null) {
                        Log.d(TAG, "Error getting document: ", error);
                        return;
                    }
                    if (document != null && document.exists()) {
                        GlobalVariables.currentUser = document.toObject(UserModel.class);
                        Log.e(TAG, "mapUser: " + GlobalVariables.currentUser.getUsername());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                });
    }

    public interface LocationCallback {
        void onLocationFound(LatLng latLng);

        void onLocationNotFound();
    }

    public static void getCurrentLocation(Activity activity, LocationCallback locationCallback) {
        // Check if user has granted location permission
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            locationCallback.onLocationNotFound();
            return;
        }
        // Create an instance of FusedLocationProviderClient
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);

        // Get the last known location
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                LatLng latLng = new LatLng(0, 0);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    // Set location to the variable and update the locationEditText
                    // Set the initial map position and zoom level
                    latLng = new LatLng(latitude, longitude);
                    locationCallback.onLocationFound(latLng);
                } else {
                    Toast.makeText(activity, "Could not get location", Toast.LENGTH_SHORT).show();
                    locationCallback.onLocationNotFound();
                }
            }
        });
    }

    public static String getDistrictName(double latitude, double longitude, Activity activity) {
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String locality = address.getLocality();
//                String subLocality = address.getSubLocality();
//                String adminArea = address.getAdminArea();
//                String countryName = address.getCountryName();
                Log.e(TAG, "getDistrictName: " + locality);
                return locality;
                //  return locality + ", " + subLocality + ", " + adminArea + ", " + countryName;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Bitmap convertImage(String base64Image) {
        byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap selectedImage = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

        return selectedImage;
    }

    public static class DatePickerFragment extends DialogFragment {
        String date = "";
        private String TAG = "DatePickerFragment";

        @Override
        public void onDetach() {
            Log.e("TAG", "onDetach: " + date);
            super.onDetach();
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            month = month + 1;
            Log.e(TAG, "onCreateDialog: " + month);
            int day = c.get(Calendar.DAY_OF_MONTH);
            // Create a new instance of DatePickerDialog and return it

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
            //   datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            return datePickerDialog;
        }

    }


    // Firebase Operations


    public static ArrayList<FarmModel> getALlFarmList() {
        ArrayList<FarmModel> farmList = new ArrayList<>();
        // Initialize list of farms
        db.collection("Farmer").document(mAuth.getUid()).collection("Farms")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e(TAG, "Error getting farms: " + e.getMessage());
                            return;
                        }

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            FarmModel farm = documentSnapshot.toObject(FarmModel.class);
                            farmList.add(farm);
                        }
                    }
                });

        return farmList;
    }


    public static void AddCultivationPlanDataToFirebase(CultivationPlanModel cultivationPlanModel, Context context) {
        db.collection("Farmer")
                .document(uid)
                .collection("cultivation_plan")
                .add(cultivationPlanModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Document added successfully
                        String documentId = documentReference.getId();
                        // Handle success
                        Toast.makeText(context, "successfully Created !", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to add document
                        // Handle failure
                        Toast.makeText(context, "Fail ! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
