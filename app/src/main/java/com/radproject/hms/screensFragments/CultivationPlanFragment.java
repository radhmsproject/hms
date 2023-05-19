package com.radproject.hms.screensFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.radproject.hms.R;
import com.radproject.hms.listAdapters.CultivationPlanAdapter;
import com.radproject.hms.models.CultivationPlanModel;
import com.radproject.hms.subActivities.CultivationPlanActivity;

import java.util.ArrayList;

public class CultivationPlanFragment extends Fragment {

    private Button addNewButton;
    private RecyclerView cultivationRecyclerView;
    private CultivationPlanAdapter cultivationAdapter;
    private ArrayList<CultivationPlanModel> cultivationPlanList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cultivation_plan, container, false);
        initViews(view);
        initClicks();
        setupRecyclerView();
        return view;
    }

    private void initViews(View view) {
        addNewButton = view.findViewById(R.id.cultivationF_addNew_btn);
        cultivationRecyclerView = view.findViewById(R.id.cultivationF_RV);
    }

    private void initClicks() {
        addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the CultivationPlanActivity
                Intent intent = new Intent(getActivity(), CultivationPlanActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupRecyclerView() {
        cultivationPlanList = new ArrayList<>(); // Initialize your list of cultivation plans

        // Create dummy data (replace with your actual data)
        CultivationPlanModel cultivationPlan1 = new CultivationPlanModel();
        cultivationPlan1.setCultivation_Plan_name("Plan 1");
        cultivationPlan1.setCultivation_CROP_ID("Crop 1");
        cultivationPlan1.setCultivation_Start_date("2023-05-19");
        cultivationPlan1.setCultivation_End_date("2023-06-19");
        cultivationPlan1.setStatus("Active");
        cultivationPlanList.add(cultivationPlan1);

        CultivationPlanModel cultivationPlan2 = new CultivationPlanModel();
        cultivationPlan2.setCultivation_Plan_name("Plan 2");
        cultivationPlan2.setCultivation_CROP_ID("Crop 2");
        cultivationPlan2.setCultivation_Start_date("2023-06-20");
        cultivationPlan2.setCultivation_End_date("2023-07-20");
        cultivationPlan2.setStatus("Inactive");
        cultivationPlanList.add(cultivationPlan2);

        // Create and set the adapter
        cultivationAdapter = new CultivationPlanAdapter(cultivationPlanList);
        cultivationRecyclerView.setAdapter(cultivationAdapter);

        // Set the layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        cultivationRecyclerView.setLayoutManager(layoutManager);
    }
}
