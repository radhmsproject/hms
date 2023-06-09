package com.radproject.hms.screensFragments;

import static android.content.ContentValues.TAG;
import static com.radproject.hms.global.GlobalMethods.getALlFarmList;
import static com.radproject.hms.global.GlobalVariables.db;
import static com.radproject.hms.global.GlobalVariables.uid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.radproject.hms.R;
import com.radproject.hms.global.GlobalVariables;
import com.radproject.hms.listAdapters.CultivationPlanAdapter;
import com.radproject.hms.listAdapters.FarmListAdapter;
import com.radproject.hms.models.CultivationPlanModel;
import com.radproject.hms.models.FarmModel;
import com.radproject.hms.subActivities.CultivationPlanActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CultivationPlanFragment extends Fragment {

    private Button addNewButton;
    private static RecyclerView cultivationRecyclerView;
    private static CultivationPlanAdapter cultivationAdapter;

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
        // Set up recycler view
        cultivationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cultivationAdapter = new CultivationPlanAdapter(cultivationList);
        cultivationRecyclerView.setAdapter(cultivationAdapter);
        getAllCultivationForCurrentFarmer();
        refreshRecycler();

    }

    @Override
    public void onResume() {
        super.onResume();
        GlobalVariables.all_farm_list = getALlFarmList();
    }

    private static CultivationPlanModel[] cultivationList = new CultivationPlanModel[0];


    // Cultivation Plan List - according to current farmer.
    public static void getAllCultivationForCurrentFarmer() {
        db.collection("Farmer").document(uid).collection("cultivation_plan").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<CultivationPlanModel> farmList = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    CultivationPlanModel cultivationPlanModel = documentSnapshot.toObject(CultivationPlanModel.class);
                    farmList.add(cultivationPlanModel);
                }
                cultivationList = farmList.toArray(new CultivationPlanModel[0]);
                Log.e(TAG, "No of Farms - " + cultivationList.length);
                cultivationAdapter.notifyDataSetChanged(); // Add this line to refresh the RecyclerView
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Error getting farms: " + e.getMessage());
            }
        });
    }

    private void refreshRecycler(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cultivationRecyclerView.setAdapter(cultivationAdapter);
            }
        }, 1000);
    }

}
