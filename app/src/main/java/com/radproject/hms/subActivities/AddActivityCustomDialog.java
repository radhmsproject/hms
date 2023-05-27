package com.radproject.hms.subActivities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.radproject.hms.R;

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

        // Rest of your code...

        initSActivitySpinner();
    }


    private void initSActivitySpinner() {
        String[] activity_type = {"Watering", "Fertilizing", "Pruning", "Mulching", "Harvesting", "Other"};
        // Create an adapter for the spinner
        ArrayAdapter<String> activity_type_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, activity_type);
        activity_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set the adapter to the statusSpinner
        activityNameSpinner.setAdapter(activity_type_adapter);
    }

    private void initFarmNameSpinner() {
        // Get Farm List from Cultivation Plan...

        String[] farmList = {"Watering", "Fertilizing", "Pruning", "Mulching", "Harvesting", "Other"};
        // Create an adapter for the spinner
        ArrayAdapter<String> farm_list_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, farmList);
        farm_list_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set the adapter to the statusSpinner
        farmNameSpinner.setAdapter(farm_list_adapter);
    }

}