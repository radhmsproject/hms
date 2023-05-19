package com.radproject.hms.subActivities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.radproject.hms.R;
import com.radproject.hms.global.GlobalMethods;
import com.radproject.hms.global.GlobalVariables;
import com.radproject.hms.listAdapters.Suggestions.AutoFarmSuggestAdapter;
import com.radproject.hms.models.CropModel;
import com.radproject.hms.models.FarmModel;

import java.util.ArrayList;

public class CultivationPlanActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private ImageButton backButton;
    private TextView navbarTextView;
    private Spinner cul1_Crop_Spinner;
    private AutoCompleteTextView farmAutoCompleteTextView;
    private RecyclerView costingItemsRecyclerView;
    private TextView startDateTextView;
    private ImageButton startDateButton;
    private TextView endDateTextView;
    private ImageButton endDateButton;
    private EditText planNameEditText;
    private Spinner statusSpinner;
    private Button createPlanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultivation_plan);

        // Call the showDatePickerDialog() method wherever you want to show the date picker


        initViews();
        initCropSpinner();
        initStatusSpinner();


    }

    private void initStatusSpinner() {
        String[] statusItems = {"Complete", "Active", "Pending"};
        // Create an adapter for the spinner
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusItems);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set the adapter to the statusSpinner
        statusSpinner.setAdapter(statusAdapter);
    }


    private void initCropSpinner() {

        Log.e(TAG, "initCropSpinner: " + GlobalVariables.crop_list);
        // Create an adapter for the spinner
        ArrayAdapter<CropModel> adapter = new ArrayAdapter<CropModel>(this, android.R.layout.simple_spinner_item, GlobalVariables.crop_list) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                CropModel crop = getItem(position);
                if (crop != null) {
                    textView.setText(crop.getCrop_name() + " - " + crop.getCrop_number());
                }
                return textView;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                CropModel crop = getItem(position);
                if (crop != null) {
                    textView.setText(crop.getCrop_name());
                }
                return textView;
            }

            @Override
            public CropModel getItem(int position) {
                return super.getItem(position);
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set the adapter to the cropSpinner
        cul1_Crop_Spinner.setAdapter(adapter);

    }

    private AutoFarmSuggestAdapter autoFarmSuggestAdapter;
    ArrayList<FarmModel> AllFarm = GlobalVariables.get_farmList;
    ArrayList<FarmModel> Filter = new ArrayList<>();

    private void initViews() {
        backButton = findViewById(R.id.cul1_back_btn);
        navbarTextView = findViewById(R.id.cul1_navbar_TV);
        cul1_Crop_Spinner = findViewById(R.id.cul1_Crop_Spinner);
        farmAutoCompleteTextView = findViewById(R.id.farm_auto_complete_text_view);
        costingItemsRecyclerView = findViewById(R.id.costing_items_list_RV);
        startDateTextView = findViewById(R.id.start_date_tv);
        startDateButton = findViewById(R.id.start_select_calender_IB);
        endDateTextView = findViewById(R.id.end_date_tv);
        endDateButton = findViewById(R.id.end_select_calender_IB);
        planNameEditText = findViewById(R.id.plan_name_edit_text);
        statusSpinner = findViewById(R.id.cul1_status);
        createPlanButton = findViewById(R.id.cul1_add_btn);


        initClicks();
        farmSuggestions();
    }

    private void farmSuggestions() {
        updateAdapter(AllFarm);
    }

    public void updateAdapter(ArrayList<FarmModel> AllFarm) {
        AutoFarmSuggestAdapter adapter = new AutoFarmSuggestAdapter(this, AllFarm);
        adapter.setNotifyOnChange(false);
        farmAutoCompleteTextView.setAdapter(adapter);
        farmAutoCompleteTextView.setTextColor(Color.RED);
        adapter.notifyDataSetChanged();
    }

    private void initClicks() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDate = true;
                showDatePickerDialog();
            }
        });
        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDate = false;
                showDatePickerDialog();
            }
        });
        farmAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemClick: ");
                FarmModel farmModel = (FarmModel) parent.getItemAtPosition(position);
                if (!Filter.contains(farmModel)) {
                    Filter.add(farmModel);
                    AllFarm.remove(farmModel);
                    updateAdapter(AllFarm);
                }
                farmAutoCompleteTextView.setText("");
            }
        });
    }


    private boolean isStartDate;

    private void showDatePickerDialog() {
        DialogFragment newFragment = new GlobalMethods.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.w("DatePicker", "Date = " + year);
        String _month = String.format("%02d", month + 1);
        String _day = String.format("%02d", dayOfMonth);

        // Update the respective TextView with the selected date
        if (isStartDate) {
            startDateTextView.setText(year + "-" + _month + "-" + _day);
            Log.e(TAG, "start: " + year + "-" + _month + "-" + _day);
        } else {
            endDateTextView.setText(year + "-" + _month + "-" + _day);
            Log.e(TAG, "end: " + year + "-" + _month + "-" + _day);
        }
    }
}
