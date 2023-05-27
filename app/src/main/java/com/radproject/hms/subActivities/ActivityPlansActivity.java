package com.radproject.hms.subActivities;

import static android.content.ContentValues.TAG;

import static com.radproject.hms.global.GlobalVariables.db;
import static com.radproject.hms.global.GlobalVariables.uid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.radproject.hms.R;
import com.radproject.hms.global.GlobalVariables;
import com.radproject.hms.listAdapters.ActivityListAdapter;
import com.radproject.hms.listAdapters.SelectedFarmAdapter2;
import com.radproject.hms.models.ActivityModel;
import com.radproject.hms.models.CultivationPlanModel;
import com.radproject.hms.models.FarmModel;

import java.io.Serializable;
import java.util.ArrayList;

public class ActivityPlansActivity extends AppCompatActivity implements Serializable {

    private ImageButton backButton;
    private TextView navbarTextView;
    private Spinner statusSpinner;
    private RecyclerView farmItemsRecyclerView;
    private TextView addedFarmsTextView;
    private TextView totalSizeTextView;
    private TextView startDateTextView;
    private ImageButton startDateButton;
    private TextView endDateTextView, cul1_Cul_ID_TV, cul1_planName_TV;
    private ImageButton endDateButton;
    private RecyclerView activityListRecyclerView;
    private Button addActivityButton;
    CultivationPlanModel cultivationPlan;
    private String cul_doc_id;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);

        cul1_Cul_ID_TV = findViewById(R.id.cul1_Cul_ID_TV);
        cul1_planName_TV = findViewById(R.id.cul1_plan_Name_TV);
        backButton = findViewById(R.id.cul1_back_btn);
        navbarTextView = findViewById(R.id.cul1_navbar_TV);
        statusSpinner = findViewById(R.id.cul1_status);
        farmItemsRecyclerView = findViewById(R.id.farm_items_list_activity_RV);
        startDateTextView = findViewById(R.id.start_date_tv);
        startDateButton = findViewById(R.id.start_select_calender_IB);
        endDateTextView = findViewById(R.id.end_date_tv);
        endDateButton = findViewById(R.id.end_select_calender_IB);
        activityListRecyclerView = findViewById(R.id.activity_list_RV);
        addActivityButton = findViewById(R.id.add_an_activity_btn);

        startDateButton.setClickable(false);
        endDateButton.setClickable(false);


        setUpStatusDropdown();


        // Retrieve the cultivationPlan bundle
        bundle = getIntent().getExtras();
        if (bundle != null) {
            cultivationPlan = (CultivationPlanModel) bundle.getSerializable("cultivationPlan");

            // Fill the components with data from cultivationPlan
            if (cultivationPlan != null) {
                navbarTextView.setText(cultivationPlan.getCultivation_Plan_name());

                cul1_Cul_ID_TV.setText(cultivationPlan.getCultivation_ID());
                cul1_planName_TV.setText(cultivationPlan.getCultivation_Plan_name());
                // Set data to other components accordingly
                startDateTextView.setText(cultivationPlan.getCultivation_Start_date());
                endDateTextView.setText(cultivationPlan.getCultivation_End_date());

                // Populate the RecyclerViews with data
                // Assuming you have adapters for both RecyclerViews

                ArrayList<FarmModel> AllFarm = GlobalVariables.all_farm_list;
                ArrayList<FarmModel> addedFarms = new ArrayList<>();
                ArrayList<String> farm_id_list = cultivationPlan.getCultivation_Farm_ID_list();
                // Iterate over each element in the farm_id_list using foreach loop

                // Iterate over each farm ID in farm_id_list
                for (String farmId : farm_id_list) {
                    boolean isFarmIdPresent = false;

                    // Iterate over each FarmModel object in AllFarm
                    for (FarmModel farm : AllFarm) {
                        if (farm.getFarmId().equals(farmId)) {
                            Log.e("TAG", "onCreate: " + farm.getFarmId());
                            // The farm ID is already present in AllFarm
                            addedFarms.add(farm);
                            isFarmIdPresent = true;
                            break; // Exit the inner loop since we found a match
                        }
                    }

                    // Check if the farm ID is present or not
                    if (isFarmIdPresent) {
                        System.out.println(farmId + " is already present in AllFarm");
                    } else {
                        System.out.println(farmId + " is not present in AllFarm");
                    }
                }

                Log.e(TAG, "onCreate: " + addedFarms);

                farmItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                SelectedFarmAdapter2 farmAdapter = new SelectedFarmAdapter2(addedFarms);
                farmItemsRecyclerView.setAdapter(farmAdapter);

            }
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back button click
                finish();
            }
        });

        addActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putSerializable("context", ActivityPlansActivity.this);
                AddActivityCustomDialog customDialog = new AddActivityCustomDialog(ActivityPlansActivity.this, bundle);
                // Replace with your activity class name                customDialog.show();
                customDialog.show();
            }
        });


        getCultivationDocumentID();
    }


    // Getting Cultivation Document ID
    private void getCultivationDocumentID() {
        Query Q = db.collection("Farmer")
                .document(uid)
                .collection("cultivation_plan")
                .whereEqualTo("cultivation_ID", cultivationPlan.getCultivation_ID());
        //      Log.e(TAG, "onCreate: " + name);
        Q.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                for (QueryDocumentSnapshot document : querySnapshot) {
                    // Access the document ID
                    cul_doc_id = document.getId();
                    bundle.putString("documentId", cul_doc_id);
                    Log.e(TAG, "onSuccess: " + cul_doc_id);
                    getAllActivityData(cul_doc_id);
                    break;
                }
                Log.e(TAG, "TASK COMPLETED");
            } else {
                Log.e("FirestoreExample", "Error getting documents: ", task.getException());
            }
        });
    }

    private void setUpStatusDropdown() {
        // setup spinner,
        String[] statusArray = {"Complete", "Pending", "Started"};
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusArray);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedStatus = (String) parent.getItemAtPosition(position);
                // Perform actions based on the selected status
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where nothing is selected (if needed)
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getAllActivityData(cul_doc_id);
            }
        }, 500);
    }


    ArrayList<ActivityModel> activityModels = new ArrayList<>();

    public void getAllActivityData(String cul_documentId) {
        db.collection("Farmer")
                .document(uid)
                .collection("cultivation_plan")
                .document(cul_documentId)
                .collection("cost_document")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // Process the retrieved documents
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            // Access the data in each document
                            String documentId = documentSnapshot.getId();

                            ActivityModel activityModel = documentSnapshot.toObject(ActivityModel.class);
                            // Perform any further operations with the retrieved data
                            activityModels.add(activityModel);
                        }
                        RefreshActivityListRecyclerView();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any errors that occurred during the retrieval
                    }
                });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private void RefreshActivityListRecyclerView() {
        activityListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //   farmItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ActivityListAdapter farmAdapter = new ActivityListAdapter(activityModels);
        activityListRecyclerView.setAdapter(farmAdapter);
    }

}
