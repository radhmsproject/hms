package com.radproject.hms.subActivities;

import static android.content.ContentValues.TAG;
import static com.radproject.hms.global.GlobalVariables.currentUser;
import static com.radproject.hms.global.GlobalVariables.db;
import static com.radproject.hms.global.GlobalVariables.mAuth;
import static com.radproject.hms.global.GlobalVariables.uid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.radproject.hms.R;
import com.radproject.hms.models.FarmModel;

public class FarmManageActivity extends AppCompatActivity {
    private EditText etName, etNumOfPerch, etLocation;
    private TextView etFarmId, navbar_TV;
    private Button btnAddFarm;


    int value1;
    String value2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_manage);
        // Get the Intent that started this activity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        value1 = bundle.getInt("key1");
        value2 = bundle.getString("key2");


        Log.e(TAG, "USER ID - " + currentUser.getUserId());
        initViews();
        initEvents();
        generateFarmID();
    }


    private void initViews() {
        // Initialize the views
        etFarmId = findViewById(R.id.et_farmID);
        etName = findViewById(R.id.et_name);
        etNumOfPerch = findViewById(R.id.et_numOfPerch);
        etLocation = findViewById(R.id.et_location);
        btnAddFarm = findViewById(R.id.btn_add_farm);
        navbar_TV = findViewById(R.id.navbar_TV);

        navbar_TV.setText(value2 + "");
    }

    private void initEvents() {


        btnAddFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addNewFarmToFirebase();
            }
        });
    }

    private void generateFarmID() {
        // Get the current farmer ID (e.g. FAM01)
        String farmerId = currentUser.getUserId();
        CollectionReference farmsRef = db.collection("Farmer").document(mAuth.getUid()).collection("Farms");

        farmsRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Get the number of farms for this farmer
                int numOfFarms = queryDocumentSnapshots.size();
                // Customize the farm ID using the farmer ID and number of farms
                String farmId = farmerId + "FARM" + String.format("%02d", Integer.valueOf(numOfFarms) + 1);
                Log.e(TAG, "onSuccess: " + numOfFarms);
                // Set the farm ID EditText field to the customized value
                etFarmId.setText(farmId);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle the failure to get the number of farms from Firebase
                Log.e(TAG, "Error getting number of farms: " + e.getMessage());
            }
        });
    }



    private void addNewFarmToFirebase() {
        // Create a new FarmModel object with the form data
        // Get the form data
        String farmId = etFarmId.getText().toString();
        String name = etName.getText().toString();
        String numOfPerchStr = etNumOfPerch.getText().toString();
        String location = etLocation.getText().toString();
        // Validate that all required fields are filled out
        if (farmId.isEmpty()) {
            etFarmId.setError("Farm ID is required");
            etFarmId.requestFocus();
            return;
        }
        if (name.isEmpty()) {
            etName.setError("Farm name is required");
            etName.requestFocus();
            return;
        }
        if (numOfPerchStr.isEmpty()) {
            etNumOfPerch.setError("Number of perch is required");
            etNumOfPerch.requestFocus();
            return;
        }
        int numOfPerch = Integer.parseInt(numOfPerchStr);
        if (location.isEmpty()) {
            etLocation.setError("Set A Location");
            etLocation.requestFocus();
            return;
        }
        // Create a new FarmModel object with the form data
        FarmModel farm = new FarmModel(farmId, name, numOfPerch, location, 0.0, 0.0);
        // Add the new farm to Firebase
        // Add the new farm to the user's document in Firebase
        db.collection("Farmer").document(mAuth.getUid()).collection("Farms")
                .add(farm)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "New farm added with ID: " + documentReference.getId());
                        // Handle the successful addition of the farm
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error adding new farm: " + e.getMessage());
                        // Handle the failure to add the farm
                    }
                });

    }
}