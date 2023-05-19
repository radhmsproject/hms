package com.radproject.hms;

import static com.radproject.hms.global.GlobalMethods.getAllCrops;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.radproject.hms.global.GlobalVariables;
import com.radproject.hms.models.CropModel;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng selectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Set up the "Select" button to return the selected location to the calling activity
        Button selectButton = findViewById(R.id.select_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selected_location", selectedLocation);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (selectedLocation != null) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("selected_location", selectedLocation);
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            setResult(RESULT_CANCELED);
            super.onBackPressed();
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getCurrentLocation();

        // Set up a listener to detect when the user clicks on the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Remove any existing markers
                mMap.clear();
                // Add a marker at the selected location and update the selectedLocation variable
                mMap.addMarker(new MarkerOptions().position(latLng));
                selectedLocation = latLng;
            }
        });
    }

    private void getCurrentLocation() {
        // Check if user has granted location permission
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        // Create an instance of FusedLocationProviderClient
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Get the last known location
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    // Set the initial map position and zoom level
                    LatLng initialLocation = new LatLng(latitude, longitude);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 12.0f));

                    // Add a marker at the initial location
                    mMap.addMarker(new MarkerOptions().position(initialLocation));
                } else {
                    Toast.makeText(MapActivity.this, "Could not get location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}