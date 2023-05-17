package com.radproject.hms.screensFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.radproject.hms.R;
import com.radproject.hms.subActivities.CultivationPlanActivity;

public class CultivationPlanFragment extends Fragment {

    private Button addNewButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cultivation_plan, container, false);
        initViews(view);
        initClicks();
        return view;
    }
    private void initViews(View view) {
        addNewButton = view.findViewById(R.id.cultivationF_addNew_btn);

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
}