package com.radproject.hms.subActivities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.radproject.hms.R;

public class CostingActivity extends AppCompatActivity {
    private TextView cultivationPlanNameTV;
    private TextView farmNameTV;
    private TextView cultivationPlantIdTV;
    private TextView activityNameTV;
    private Spinner costItemSpinner;
    private Spinner unitOfMeasurementSpinner;
    private EditText costRemarkET;
    private EditText numberOfUnitsET;
    private EditText unitCostET;
    private Button deleteButton;
    private Button updateButton;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costing);

        cultivationPlanNameTV = findViewById(R.id.cultivation_plan_nameTV);
        farmNameTV = findViewById(R.id.farm_nameTV);
        cultivationPlantIdTV = findViewById(R.id.cultivation_plant_idTV);
        activityNameTV = findViewById(R.id.activity_nameTV);
        costItemSpinner = findViewById(R.id.COST_cost_item_spinner);
        unitOfMeasurementSpinner = findViewById(R.id.COST_unit_of_measurement_spinner);
        costRemarkET = findViewById(R.id.Cost_remarkET);
        numberOfUnitsET = findViewById(R.id.COST_number_of_units_et);
        unitCostET = findViewById(R.id.COST_unit_cost_et);
        deleteButton = findViewById(R.id.COST_Delete_IB);
        updateButton = findViewById(R.id.COST_Update_IB);
        addButton = findViewById(R.id.COST_add_IB);

        // Add your logic or listeners for the buttons and other views here
    }
}
