package com.radproject.hms;

import static android.content.ContentValues.TAG;

import static com.radproject.hms.global.GlobalMethods.getCurrentLocation;
import static com.radproject.hms.global.GlobalMethods.getDistrictName;
import static com.radproject.hms.global.GlobalVariables.db;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.radproject.hms.global.GlobalMethods;
import com.radproject.hms.models.UserModel;

public class RegisterActivity extends AppCompatActivity implements GlobalMethods.LocationCallback {

    private EditText usernameET, mobileET, emailET, locationET, passwordET, confirmPasswordET;
    private TextView registerButtonTV, loginClickTV;
    private ProgressBar progress_circular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameET = findViewById(R.id.username_ET);
        mobileET = findViewById(R.id.mobile_ET);
        emailET = findViewById(R.id.email_ET);
        locationET = findViewById(R.id.location_ET);
        passwordET = findViewById(R.id.password_ET);
        confirmPasswordET = findViewById(R.id.confirm_password_ET);
        registerButtonTV = findViewById(R.id.register_buttonTV);
        loginClickTV = findViewById(R.id.loginClickTV);
        progress_circular = findViewById(R.id.progress_circular);

        callGetCurrentLocation(this);
        isProgress(false);
        initClickEvents();
    }

    private void initClickEvents() {
        registerButtonTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement your registration logic here
                if (validateFields()) {
                    Register();
                } else {
                    Toast.makeText(RegisterActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                }

            }
        });

        loginClickTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch login activity here
                GlobalMethods.redirectToLogin(RegisterActivity.this);
            }
        });
    }

    String username, mobile, email, location, password, confirmPassword;
    Double lat, lng;


    private void Register() {

        isProgress(true);

        username = usernameET.getText().toString();
        mobile = mobileET.getText().toString();
        email = emailET.getText().toString();

        location = locationET.getText().toString();
        password = passwordET.getText().toString();
        confirmPassword = confirmPasswordET.getText().toString();


        // Get the last user ID from Firestore and increment it for the new user
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Farmer").orderBy("userId", Query.Direction.DESCENDING).limit(1).get().addOnSuccessListener(queryDocumentSnapshots -> {
            int lastUserId = 0;
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                UserModel userModel = document.toObject(UserModel.class);
                String userId = userModel.getUserId();
                if (userId.startsWith("FAM-")) {
                    String number = userId.replace("FAM", "");
                    lastUserId = Integer.parseInt(number);
                }
            }

            // Increment the last user ID for the new user
            int newUserId = lastUserId + 1;
            String userId = "FAM" + newUserId;

            // Register the user with Firebase Authentication using the email and password
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
                // Create a new UserModel instance with the input values and the user ID
                UserModel userModel = new UserModel(userId, username, mobile, email, location, 0.0, 0.0, false, false, false);

                // Save the UserModel instance to the Firestore database
                db.collection("Farmer").document(authResult.getUser().getUid()).set(userModel).addOnSuccessListener(aVoid -> {
                    // Registration and data saving succeeded
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                    isProgress(false);
                    finish(); // Close the registration activity
                    GlobalMethods.redirectToLogin(RegisterActivity.this);
                }).addOnFailureListener(e -> {
                    // Data saving failed
                    isProgress(false);
                    Toast.makeText(this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }).addOnFailureListener(e -> {
                // Registration failed
                isProgress(false);
                Toast.makeText(this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });

        }).addOnFailureListener(e -> {
            // Failed to get the last user ID
            isProgress(false);
            Toast.makeText(this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }


    public boolean validateFields() {
        username = usernameET.getText().toString();
        mobile = mobileET.getText().toString();
        email = emailET.getText().toString();
        location = locationET.getText().toString();
        password = passwordET.getText().toString();
        confirmPassword = confirmPasswordET.getText().toString();
        boolean isValid = true;
        if (TextUtils.isEmpty(username) || username.length() < 3 || username.length() > 30) {
            usernameET.setError("Please enter a valid username (3-30 characters)");
            isValid = false;
        }
        if (TextUtils.isEmpty(mobile) || !mobile.matches("^(?:\\+94|0)[0-9]{9,10}$")) {
            mobileET.setError("Please enter a valid Sri Lankan mobile number");
            isValid = false;
        }
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.setError("Please enter a valid email address");
            isValid = false;
        }
        if (TextUtils.isEmpty(location)) {
            locationET.setError("Please enter a valid location");
            isValid = false;
        }
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) || password.length() < 6) {
            passwordET.setError("Please enter a valid password (minimum 6 characters)");
            confirmPasswordET.setError("Please enter a valid password (minimum 6 characters)");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            passwordET.setError("Passwords do not match");
            confirmPasswordET.setError("Passwords do not match");
            isValid = false;
        }

        return isValid;
    }


    private void isProgress(Boolean isProgress) {
        if (isProgress) {
            registerButtonTV.setVisibility(View.GONE);
            progress_circular.setVisibility(View.VISIBLE);
        } else {
            registerButtonTV.setVisibility(View.VISIBLE);
            progress_circular.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLocationFound(LatLng latLng) {
        lat = latLng.latitude;
        lng = latLng.longitude;
        Log.e(TAG, "onLocationFound: " + latLng);
        locationET.setText(getDistrictName(lat, lng, this));

    }

    @Override
    public void onLocationNotFound() {
        Log.e(TAG, "onLocationNotFound: ");
    }

    public void callGetCurrentLocation(Activity activity) {
        getCurrentLocation(activity, this);
    }
}