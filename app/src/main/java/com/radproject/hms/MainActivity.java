package com.radproject.hms;

import static android.content.ContentValues.TAG;
import static com.radproject.hms.global.GlobalMethods.getALlFarmList;
import static com.radproject.hms.global.GlobalMethods.listenForUserChanges;
import static com.radproject.hms.global.GlobalMethods.redirectToLogin;
import static com.radproject.hms.global.GlobalVariables.mAuth;
import static com.radproject.hms.global.GlobalVariables.uid;
import static com.radproject.hms.global.PublicFirebaseMethods.getAllCrops;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.radproject.hms.global.GlobalVariables;
import com.radproject.hms.global.PublicFirebaseMethods;
import com.radproject.hms.models.CultivationPlanModel;
import com.radproject.hms.screensFragments.ActivityPlansFragment;
import com.radproject.hms.screensFragments.CultivationPlanFragment;
import com.radproject.hms.screensFragments.DashboardFragment;
import com.radproject.hms.screensFragments.FarmFragment;
import com.radproject.hms.screensFragments.FarmerProfileFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        getData();
        // Initialize Bottom Navigation View
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(navListener);

        // Display the default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new DashboardFragment()).commit();
        Log.e(TAG, "onCreate: " + mAuth.getUid());
        if (mAuth.getUid() == null) {
            Log.e(TAG, "onCreate: " + mAuth.getUid());
            redirectToLogin(MainActivity.this);
        } else {
            listenForUserChanges(mAuth.getUid());
        }
    }

    private void getData() {
        GlobalVariables.all_crop_list = getAllCrops();
        GlobalVariables.all_farm_list = getALlFarmList();


    }

    // Bottom Navigation View Listener
    private BottomNavigationView.OnItemSelectedListener navListener =
            new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_dashboard:
                            selectedFragment = new DashboardFragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new FarmerProfileFragment();
                            break;
                        case R.id.nav_activity_plans:
                            selectedFragment = new ActivityPlansFragment();
                            break;
                        case R.id.nav_cultivation_plans:
                            selectedFragment = new CultivationPlanFragment();
                            break;
                        case R.id.nav_farms:
                            selectedFragment = new FarmFragment();
                            break;


                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}