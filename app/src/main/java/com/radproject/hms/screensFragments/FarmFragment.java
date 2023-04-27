package com.radproject.hms.screensFragments;

import static android.content.ContentValues.TAG;
import static com.radproject.hms.global.GlobalVariables.db;
import static com.radproject.hms.global.GlobalVariables.mAuth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.radproject.hms.R;
import com.radproject.hms.listAdapters.FarmListAdapter;
import com.radproject.hms.models.FarmModel;
import com.radproject.hms.subActivities.FarmManageActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FarmFragment extends Fragment {

    private FarmModel[] mFarmList = new FarmModel[0];
    private FarmListAdapter mAdapter;
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize list of items
        //  mItemList = null;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_farm, container, false);

        // Set up search bar
        SearchView searchView = rootView.findViewById(R.id.search_view);
        Button add_new_button = rootView.findViewById(R.id.add_new_button);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform search
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Update search results
                return true;
            }
        });

        // Set up "Add New" button
        Button addNewButton = rootView.findViewById(R.id.add_new_button);
        addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
            }
        });

        add_new_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FarmManageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("key1", 0);
                bundle.putString("key2", "Add New Farm");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        // Set up recycler view
        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new FarmListAdapter(mFarmList);
        recyclerView.setAdapter(mAdapter);
        getFarmListFromFirebase();
        return rootView;
    }

    private void getFarmListFromFirebase() {
        // Initialize list of farms
        db.collection("Farmer").document(mAuth.getUid()).collection("Farms")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e(TAG, "Error getting farms: " + e.getMessage());
                            return;
                        }

                        List<FarmModel> farmList = new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            FarmModel farm = documentSnapshot.toObject(FarmModel.class);
                            farmList.add(farm);
                        }
                        mFarmList = farmList.toArray(new FarmModel[0]);
                        Log.e(TAG, "No of Farms - " + mFarmList.length);

                        mAdapter = new FarmListAdapter(mFarmList);
                        recyclerView.setAdapter(mAdapter);
                    }
                });
    }

}