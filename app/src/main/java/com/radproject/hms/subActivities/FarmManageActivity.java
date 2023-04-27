package com.radproject.hms.subActivities;

import static android.content.ContentValues.TAG;
import static com.radproject.hms.global.GlobalVariables.currentUser;
import static com.radproject.hms.global.GlobalVariables.db;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.radproject.hms.R;

public class FarmManageActivity extends AppCompatActivity {
    private EditText etName, etNumOfPerch, etLocation;
    private TextView etFarmId;
    private Button btnAddFarm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_manage);
        // Get the Intent that started this activity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String value1 = bundle.getString("key1");
        int value2 = bundle.getInt("key2");

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
    }

    private void initEvents() {

    }

    private void generateFarmID() {
        // Get the current farmer ID (e.g. FAM01)
        String farmerId = currentUser.getUserId();
        CollectionReference farmsRef = db.collection("farms");
        Query query = farmsRef.whereEqualTo("farmerId", farmerId);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Get the number of farms for this farmer
                int numOfFarms = queryDocumentSnapshots.size();
                // Customize the farm ID using the farmer ID and number of farms
                String farmId = farmerId + "FARM" + String.format("%02d", numOfFarms + 1);
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
}