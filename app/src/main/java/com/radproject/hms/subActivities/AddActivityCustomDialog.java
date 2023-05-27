package com.radproject.hms.subActivities;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.radproject.hms.R;
import com.radproject.hms.global.GlobalVariables;
import com.radproject.hms.models.ActivityModel;
import com.radproject.hms.models.CultivationPlanModel;
import com.radproject.hms.models.FarmModel;

import java.util.ArrayList;

public class AddActivityCustomDialog extends Dialog {

    private Bundle dataBundle;

    public AddActivityCustomDialog(Context context, Bundle dataBundle) {
        super(context);
        this.dataBundle = dataBundle;
    }

    private Spinner activityNameSpinner;
    private Spinner farmNameSpinner;
    private TextView startDateTextView;
    private ImageButton startDateSelectImageButton;
    private Button addFarmButton;
    CultivationPlanModel cultivationPlan = null;
    String farmID;
    String farmName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity); // Replace "your_layout_file" with the actual layout file name

        // Initialize the variables
        activityNameSpinner = findViewById(R.id.Activity_NameSP);
        farmNameSpinner = findViewById(R.id.Farm_NameSP);
        startDateTextView = findViewById(R.id.start_date_tv);
        startDateSelectImageButton = findViewById(R.id.start_select_calender_IB);
        addFarmButton = findViewById(R.id.btn_add_farm);

        initClicks();
        initSActivitySpinner();
        // Retrieve the CultivationPlanModel from the dataBundle
        if (dataBundle != null) {
            cultivationPlan = (CultivationPlanModel) dataBundle.getSerializable("cultivationPlan");
            ArrayList<String> farm_id_list = cultivationPlan.getCultivation_Farm_ID_list();
            ArrayList<String> filteredFarmNames = new ArrayList<>();
            for (String farmId : farm_id_list) {
                for (FarmModel farm : GlobalVariables.all_farm_list) {
                    if (farm.getFarmId().equals(farmId)) {
                        String farmIdAndName = farm.getFarmId() + " : " + farm.getName();
                        filteredFarmNames.add(farmIdAndName);
                        break;
                    }
                }
            }
            initFarmNameSpinner(filteredFarmNames);
        }
    }

    private void initClicks() {
        addFarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityModel activityModel = new ActivityModel();
                // Validate cultivation ID
                if (cultivationPlan != null && cultivationPlan.getCultivation_ID() != null) {
                    activityModel.setCultivation_id(cultivationPlan.getCultivation_ID());
                } else {
                    Toast.makeText(getContext(), "Cultivation not found", Toast.LENGTH_SHORT).show();
                    return; // Exit the onClick() method to prevent dismissing the dialog
                }
                // Validate activity name
                String selectedActivityName = activityNameSpinner.getSelectedItem().toString();
                if (selectedActivityName != null && !selectedActivityName.isEmpty()) {
                    activityModel.setActivity_name(selectedActivityName);
                } else {
                    Toast.makeText(getContext(), "Please select an activity", Toast.LENGTH_SHORT).show();
                    return; // Exit the onClick() method to prevent dismissing the dialog
                }
                // Validate farm ID
                if (farmID != null && !farmID.isEmpty()) {
                    activityModel.setFarm_id(farmID);
                } else {
                    Toast.makeText(getContext(), "Please select a farm", Toast.LENGTH_SHORT).show();
                    return; // Exit the onClick() method to prevent dismissing the dialog
                }
                // Validate planned date
                String plannedDate = startDateTextView.getText().toString();
                if (plannedDate != null && !plannedDate.isEmpty()) {
                    activityModel.setPlanned_date(plannedDate);
                } else {
                    Toast.makeText(getContext(), "Date Not Selected", Toast.LENGTH_SHORT).show();
                    return; // Exit the onClick() method to prevent dismissing the dialog
                }
                // All data is valid, proceed with further actions
                dismiss(); // Dismiss the dialog
                // Add the activityModel to your desired logic or data structure
            }
        });

        farmNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String input = farmNameSpinner.getSelectedItem().toString();
                String[] parts = input.split(":");
                farmID = parts[0].trim();
                farmName = parts[1].trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void initSActivitySpinner() {
        String[] activity_type = {"Watering", "Fertilizing", "Pruning", "Mulching", "Harvesting", "Other"};
        // Create an adapter for the spinner
        ArrayAdapter<String> activity_type_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, activity_type);
        activity_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set the adapter to the statusSpinner
        activityNameSpinner.setAdapter(activity_type_adapter);
    }

    private void initFarmNameSpinner(ArrayList<String> farms) {
        // Create an ArrayList to hold the filtered farm IDs and names
        // Create an adapter for the spinner using the filteredFarmNames list
        ArrayAdapter<String> farm_list_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, farms);
        farm_list_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set the adapter to the farmNameSpinner
        farmNameSpinner.setAdapter(farm_list_adapter);

    }


}