package com.radproject.hms.subActivities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.radproject.hms.R;
import com.radproject.hms.global.GlobalVariables;
import com.radproject.hms.listAdapters.SelectedFarmAdapter;
import com.radproject.hms.listAdapters.SelectedFarmAdapter2;
import com.radproject.hms.models.CultivationPlanModel;
import com.radproject.hms.models.FarmModel;

import java.util.ArrayList;

public class ActivityPlansActivity extends AppCompatActivity {

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

        // Retrieve the cultivationPlan bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            CultivationPlanModel cultivationPlan = (CultivationPlanModel) bundle.getSerializable("cultivationPlan");

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

                Log.e(TAG, "All Farms : "+AllFarm.size() );
                SelectedFarmAdapter2 costingItemsAdapter = new SelectedFarmAdapter2(AllFarm, this);
                farmItemsRecyclerView.setAdapter(costingItemsAdapter);

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
                // Handle add activity button click
                // Add your code here
            }
        });
    }
}
